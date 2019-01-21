package com.dke.game.Views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.dke.game.Controller.MainLoop;
import com.dke.game.Controller.ViewManager;
import com.dke.game.Models.AI.OnlineEvolution.Evolution;
import com.dke.game.Models.DataStructs.Board;

import java.util.Arrays;

import static com.dke.game.Controller.MainLoop.skin;

public class OptionsView extends View {

    private Stage stage;
    private TextButton textButton;
    private TextButton playerSetup;
    private TextButton generalSettings;
    private TextButton videoSettings;
    private CheckBox debug;
    private CheckBox opponentModeling;
    private CheckBox opponentModeling2;
    private MenuView menuView;
    private SelectBox<String> boardSize;
    private SelectBox<String> algorithms;
    private SelectBox<String> algorithms2;
    private String p1;
    private String p2;
    public static String SELECTED_ALGORITHM_1 = "Evolution";
    public static boolean OPPONENT_MODELING_1 = false;
    public static String SELECTED_ALGORITHM_2 = "Evolution";
    public static boolean OPPONENT_MODELING_2 = false;


    protected OptionsView(ViewManager viewManager, MenuView menuView) {
        super(viewManager);
        this.menuView = menuView;
        //-----------------------
        //Default setting
        this.p1 = "Human";
        this.p2 = "Human";
        //-----------------------
    }

    @Override
    protected void handleInput() {
        super.handleInput();
    }

    @Override
    public void create() {
        stage = new Stage();

        Table settings = new Table();
        settings.setFillParent(true);
        settings.left().padLeft(20);
        //settings.debugAll();

        generalSettings = new TextButton("General settings", skin, "menu");
        generalSettings.getLabel().setAlignment(Align.left);
        settings.add(generalSettings).width(200);
        settings.row();

        playerSetup = new TextButton("Player-setup", skin, "menu");
        playerSetup.getLabel().setAlignment(Align.left);
        settings.add(playerSetup).fillX();
        settings.row();

        videoSettings = new TextButton("Video settings", skin, "menu");
        videoSettings.getLabel().setAlignment(Align.left);
        settings.add(videoSettings).fillX();
        settings.row();


//        settings = new List(MainLoop.skin);
//        String[] settingsContent = {"General settings","Player-Setup","Video Settings"};
//        settings.setItems(settingsContent);
//
//        Table menuBar = new Table();
//        menuBar.left();
//        menuBar.setFillParent(true);
//        menuBar.add(settings);

        Table table = new Table();
        Table titleTable = new Table();
        titleTable.top();

        Label title = new Label("Options Menu", skin,"title");
        titleTable.add(title).padTop(200);
        //table.row();

        debug = new CheckBox("Debug mode ",MainLoop.skin);
        if(Evolution.debugPrinting){
            debug.setChecked(true);
        }
        table.add(debug).uniform();
        table.row();

        String[] boardSizeOptions = {"5x6","10x10"};
        boardSize = new SelectBox<>(skin);
        boardSize.setItems(boardSizeOptions);
        table.add(boardSize).uniform();
        table.row();
        if(p1.equals("AI") || p2.equals("AI")) {
            Table algorithmChoices = new Table();
            String[] algorithmOptions = {"Evolution", "Greedy", "Alpha-Beta", "Monte-Carlo"};
            algorithms = new SelectBox<>(skin);
            algorithms.setItems(algorithmOptions);
            algorithmChoices.add(algorithms).uniform();

            if (p1.equals("AI") && p2.equals("AI")) {
                algorithms2 = new SelectBox<>(skin);
                algorithms2.setItems(algorithmOptions);
                algorithmChoices.add(algorithms2).uniform();
            }
            table.add(algorithmChoices).uniform();
            table.row();
        }
        if(p1.equals("AI") || p2.equals("AI")) {
            Table options = new Table();
            opponentModeling = new CheckBox("Opponent modeling for algorithm 1: ", skin);
            if (OPPONENT_MODELING_1) {
                opponentModeling.setChecked(true);
            }
            options.add(opponentModeling).uniform();
            if (p1.equals("AI") && p2.equals("AI")) {
                opponentModeling2 = new CheckBox("Opponent modeling for algorithm 2: ", skin);
                if (OPPONENT_MODELING_2) {
                    opponentModeling2.setChecked(true);
                }
                options.add(opponentModeling2).uniform();
            }
            table.add(options).uniform();
            table.row();
        }

        textButton = new TextButton("Return", skin);
        table.add(textButton).bottom();
        table.row();


//        titleTable.debugAll();
//        table.debugAll();
        table.setFillParent(true);
        titleTable.setFillParent(true);

        createListeners();
        stage.addActor(settings);
        stage.addActor(titleTable);
        stage.addActor(table);
    }
    private void createListeners(){
        if(algorithms != null) {
            algorithms.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    if (algorithms.getSelected().equals("Evolution")) {
                        opponentModeling.setVisible(true);
                    } else {
                        opponentModeling.setVisible(false);
                    }
                }
            });
        }
        if (algorithms2!=null) {
            algorithms2.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    if (algorithms2.getSelected().equals("Evolution")) {
                        opponentModeling2.setVisible(true);
                    } else {
                        opponentModeling2.setVisible(false);
                    }
                }
            });
        }
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
                if (p1.equals("AI") || p2.equals("AI")) {
                    SELECTED_ALGORITHM_1 = algorithms.getSelected();
                    if (opponentModeling.isChecked()) {
                        OPPONENT_MODELING_1 = true;
                    } else {
                        OPPONENT_MODELING_1 = false;
                    }
                    if(p1.equals("AI") && p2.equals("AI")) {
                        SELECTED_ALGORITHM_2 = algorithms2.getSelected();
                        if (opponentModeling.isChecked()) {
                            OPPONENT_MODELING_2 = true;
                        } else {
                            OPPONENT_MODELING_2 = false;
                        }
                    }
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
