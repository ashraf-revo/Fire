package org.revo;

import java.util.Arrays;

import static org.revo.Fire.*;

/**
 * Created by ashraf on 15/09/16.
 */
public class FireTest {
    public static void main(String[] args) {
        get("/{name}", m -> new User(m.get("name")));
        get("/", m -> Arrays.asList(new User("ashraf"), new User("revo")));

        post("/{name}", User.class, (m, user) -> new User(user.getName() + m.get("name")));

        try {
            start(8080, "");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}