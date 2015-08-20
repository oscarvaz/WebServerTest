package controllers;

import play.*;
import play.mvc.*;

import views.html.index;

public class Application extends Controller {

    public static Result index() {

        return ok(index.render("Que"));
    }

}

public static Result addBar() {
    Bar bar = form.form(Bar.class).bindFromRequest).get();
    bear.save();
    return redirect (routes.Application.index());
}