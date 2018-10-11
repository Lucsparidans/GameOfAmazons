package com.dke.game.Views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.dke.game.Controller.GameLoop;
import com.dke.game.Controller.MainLoop;
import com.dke.game.Controller.ViewManager;


public class MenuView extends View {

    private Stage stage;
    private TextButton textButton;


    public MenuView(ViewManager viewManager) {
        super(viewManager);
    }

    @Override
    public void create() {
        stage = new Stage();


        Table table = new Table();
        table.setFillParent(true);

        Label title = new Label("Game of Amazons", MainLoop.skin,"title");
        table.add(title).center();
        table.row();

        textButton = new TextButton("Click me", MainLoop.skin);
        table.add(textButton).center();
        createListeners();

        stage.addActor(table);

    }

    private void createListeners(){
        textButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                new GameLoop(viewManager);
            }
        });
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
