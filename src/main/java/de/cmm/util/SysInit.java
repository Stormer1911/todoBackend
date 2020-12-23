package de.cmm.util;

import de.cmm.model.TodoModel;
import de.cmm.model.TodoSys;
import io.quarkus.runtime.StartupEvent;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class SysInit {
    private static final Logger LOG = Logger.getLogger(SysInit.class);

    @ConfigProperty(name = "demo.data", defaultValue = "false")
    boolean demoData;

    @Transactional
    void onStart(@Observes StartupEvent event) {
        LOG.info("systable initialized: " + initSysTable());
        if (Boolean.TRUE.equals(demoData)) {
            initDemoData();
        } else {
            LOG.info("demo data will not be initialized");
        }
    }

    private void initDemoData() {
        List<TodoSys> shopSys = TodoSys.listAll();
        shopSys.forEach(i -> {
                    if (Boolean.TRUE.equals(i.initialized)) {
                        LOG.info(i.value + " is initialized: " + i.initialized);
                    } else {
                        switch (i.value) {
                            case "todo":
                                if (Boolean.FALSE.equals(i.initialized)) {
                                    LOG.info("initializing todo examples:");
                                    i.initialized = initUser();
                                    LOG.info("DONE " + i.initialized);
                                }
                                break;

                            default:
                                LOG.error("Error in System Table" + i.value + "not recognized");
                                System.exit(1);
                        }

                    }
                }
        );
    }

    private boolean initSysTable() {
        List<TodoSys> shopSys = new ArrayList<>();
        shopSys.add(TodoSys.findByName("todo") != null ? TodoSys.findByName("todo") : new TodoSys("todo", false));
        shopSys.forEach(i -> {
            if (!i.isPersistent()) {
                i.persist();
            }
        });
        return true;
    }

    public boolean initUser() {
        try (Jsonb jsonb = JsonbBuilder.create()) {
            List<TodoModel> todoModels = jsonb.fromJson(getClass().getResourceAsStream("/TestData/todo.json"), new ArrayList<TodoModel>() {
            }.getClass().getGenericSuperclass());
            todoModels.forEach(todo -> {
                TodoModel model = new TodoModel();
                model.done = todo.done;
                model.subtitle = todo.subtitle;
                model.title = todo.title;
                model.persist();
                LOG.debug("added todoItem: " + todo.toString());
            });
            LOG.info(TodoModel.count() + " Todos angelegt");
        } catch (Exception e) {
            LOG.error("Fehler bei ToDo Initialisierung: " + e.toString());
        }
        return true;
    }

}
