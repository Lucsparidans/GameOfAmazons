package com.dke.game.Views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.dke.game.Controller.GameLoop;
import com.dke.game.Controller.Player.Player;
import com.dke.game.Controller.ViewManager;

import static com.dke.game.Controller.MainLoop.skin;


/**
 * Menu
 */

public class MenuView extends View {


    private Stage stage;
    private TextButton textButton;
    private TextButton settingsButton;
    private SelectBox<String> player1;
    private SelectBox<String> player2;
    private Player w;
    private Player b;
    private MenuView menuView = this;


    public MenuView(ViewManager viewManager) {
        super(viewManager);
    }

    @Override
    public void create() {
        stage = new Stage();

        Table table = new Table();
        Label title = new Label("Game of Amazons", skin,"title");
        table.add(title).top().padTop(200).padBottom(20);
        table.row();

        textButton = new TextButton("Click me", skin);
        table.add(textButton).bottom().padBottom(200).fillX();
        table.row();

        //table.debugAll();
        table.setFillParent(true);

        Table settings = new Table(skin);
        settings.setBackground("menu-bg");
        settings.left().padLeft(20);
        settings.setFillParent(true);


        settings.row();
        settingsButton = new TextButton("Settings", skin, "menu");
        settingsButton.getLabel().setAlignment(Align.left);
        settings.add(settingsButton).width(200);


        stage.addActor(settings);
        stage.addActor(table);
        createListeners();

    }

    private void createListeners(){
        textButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                new GameLoop(viewManager,player1.getSelected(),player2.getSelected());
            }
        });
        settingsButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                viewManager.push(new OptionsView(viewManager,menuView));
            }
        });

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void render() {
        super.handleInput();
        if (stage != null) {
            stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
            Gdx.input.setInputProcessor(stage);
            stage.act();
            stage.draw();
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)){
            new GameLoop(viewManager);

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
