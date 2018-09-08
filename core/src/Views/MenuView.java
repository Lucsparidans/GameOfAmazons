package Views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.dke.game.Controller.MainLoop;
import com.dke.game.Controller.ViewManager;

public class MenuView extends View {

    private Stage stage;

    public MenuView(ViewManager viewManager) {
        super(viewManager);
    }

    @Override
    public void create() {
        stage = new Stage(new ScalingViewport(Scaling.fit, Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight()));
        stage.addActor(new TextButton("hey", MainLoop.skin));

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void render() {
        if (stage != null) {
            stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
            Gdx.input.setInputProcessor(stage);
            stage.act();
            stage.draw();
        }
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }

}
