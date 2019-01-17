package com.dke.game.Views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.dke.game.Controller.GameLoop;
import com.dke.game.Controller.MainLoop;
import com.dke.game.Controller.ViewManager;
import com.dke.game.Models.AI.OnlineEvolution.Evolution;
import com.dke.game.Models.DataStructs.Board;
import javafx.scene.control.ComboBox;

import java.util.Arrays;

import static com.dke.game.Controller.MainLoop.skin;

public class OptionsView extends View {

    private Stage stage;
    private TextButton textButton;
    private CheckBox debug;
    private SelectBox<String> boardSize;


    protected OptionsView(ViewManager viewManager) {
        super(viewManager);
    }

    @Override
    protected void handleInput() {
        super.handleInput();
    }

    @Override
    public void create() {
        stage = new Stage();


        Table table = new Table();

        Label title = new Label("Options Menu", skin,"title");
        table.add(title).expand().top();
        table.row();

        debug = new CheckBox("Debug mode ",MainLoop.skin);
        if(Evolution.debugPrinting){
            debug.setChecked(true);
        }
        table.add(debug);
        table.row();

        String[] boardSizeOptions = {"10x10","5x6"};
        boardSize = new SelectBox<String>(skin);
        boardSize.setItems(boardSizeOptions);
        table.add(boardSize);
        table.row();


        textButton = new TextButton("Return", skin);
        table.add(textButton).expand().bottom();
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


        createListeners();
        stage.addActor(table);
    }
    private void createListeners(){
        textButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(debug.isChecked()){
                    Evolution.debugPrinting = true;
                }
                else{
                    Evolution.debugPrinting = false;
                }
                if(boardSize.getSelected().equals("10x10")){
                    Board.boardSize = Board.BoardSize.TENxTEN;
                }
                else if(boardSize.getSelected().equals("5x6")){
                    Board.boardSize = Board.BoardSize.FIVExSIX;
                }
                viewManager.pop();
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
