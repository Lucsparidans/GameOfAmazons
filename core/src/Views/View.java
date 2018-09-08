package Views;


import com.badlogic.gdx.ApplicationListener;
import com.dke.game.Controller.ViewManager;


public abstract class View implements ApplicationListener {
    protected ViewManager viewManager;
    protected View(ViewManager viewManager){
        this.viewManager = viewManager;
    }
}
