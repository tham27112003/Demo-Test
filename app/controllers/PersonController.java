package controllers;

import models.Person;
import models.PersonRepository;
import play.data.FormFactory;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import scala.collection.JavaConverters;
import java.util.List;

import javax.inject.Inject;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;

import static play.libs.Json.toJson;

/**
 * The controller keeps all database operations behind the repository, and uses
 * {@link HttpExecutionContext} to manage execution context for async operations.
 */
public class PersonController extends Controller {

    private final FormFactory formFactory;
    private final PersonRepository personRepository;
    private final HttpExecutionContext ec;

    @Inject
    public PersonController(FormFactory formFactory, PersonRepository personRepository, HttpExecutionContext ec) {
        this.formFactory = formFactory;
        this.personRepository = personRepository;
        this.ec = ec;
    }

    // Render the index page and pass the request
    public CompletionStage<Result> index(final Http.Request request) {
        return personRepository.list()
                .thenApplyAsync(personStream -> {
                    // Chuyển đổi Stream<Person> thành List<Person>
                    List<Person> persons = personStream.collect(Collectors.toList());
                    // Chuyển đổi List<Person> thành Seq<Person> bất biến
                    scala.collection.immutable.Seq<models.Person> personSeq =
                            JavaConverters.asScala(persons).toList(); // toList() để chuyển đổi thành Seq bất biến
                    return ok(views.html.index.render(request, personSeq));
                }, ec.current());
    }


    // Method to add a new person to the r
    // epository
    public CompletionStage<Result> addPerson(final Http.Request request) {
        Person person = formFactory.form(Person.class).bindFromRequest(request).get();
        return personRepository
                .add(person)
                .thenApplyAsync(p -> redirect(routes.PersonController.index()), ec.current());
    }

    // Method to retrieve all persons as a JSON list
    public CompletionStage<Result> getPersons() {
        return personRepository
                .list()
                .thenApplyAsync(personStream -> ok(toJson(personStream.collect(Collectors.toList()))), ec.current());
    }

}
