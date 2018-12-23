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


    public MenuView(ViewManager viewManager) {
        super(viewManager);
    }

    @Override
    public void create() {
        stage = new Stage();


        Table table = new Table();

        Label title = new Label("Game of Amazons", skin,"title");
        table.add(title).top();
        table.row();

        textButton = new TextButton("Click me", skin);
        table.add(textButton).center();
        table.row();

        String[] content = {"Human", "AI"};
        //String[] c2 = {"AI","Human"};
        player1 = new SelectBox<String>(skin);
        player1.setItems(content);
        player2 = new SelectBox<String>(skin);
        player2.setItems(content);
        Table players = new Table();
        players.add(new Label("White", skin));
        players.add(new Label("Black", skin));
        players.row();
        players.add(player1);
        players.add(player2);
        table.add(players).center();
        table.row();
        //table.debugAll();
        table.setFillParent(true);
        float pad = 1/100f * stage.getWidth();



//        List settings = new List(MainLoop.skin);
//        String[] settingsContent = {"Settings", "Difficulty", "Player-Setup", "Video-Settings"};
//        settings.setItems(settingsContent);
//
//
//
//// align all actors to the top of the screen
//        Table menuBar = new Table();
//        menuBar.setFillParent(true);
//        menuBar.add(settings).expand().left().fillY(); // set to screen width
//
//        stage.addActor(menuBar);
        Table settings = new Table(skin);
        settings.setBackground("menu-bg");
        settings.left();
        settings.setFillParent(true);
        ButtonGroup buttonGroup = new ButtonGroup();
        settings.row();
        settingsButton = new TextButton("Settings", skin, "menu");
        settingsButton.getLabel().setAlignment(Align.left);
        buttonGroup.add(settingsButton);
        settings.add(settingsButton);
        settings.add(settingsButton).top();

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
                System.out.println("TO BE IMPLEMENTED");
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
