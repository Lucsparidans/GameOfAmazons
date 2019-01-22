package com.dke.game.Views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.dke.game.Controller.MainLoop;
import com.dke.game.Controller.ViewManager;
import com.dke.game.Models.AI.OnlineEvolution.Evolution;
import com.dke.game.Models.DataStructs.Board;


import java.awt.*;

import static com.dke.game.Controller.MainLoop.skin;

public class OptionsView extends View {

    private TextButton UIdebugger = new TextButton("Debug UI",skin);

    private Stage stage;
    private boolean debugUI = false;
    private boolean showingMenu = true;
    private boolean showOptions = false;
    private Table menuDisplayed;
    private Table settings;
    private Table generalSettingsTable;
    private Table playerSetupTable;
    private Table videoSettingsTable;
    private Table titleTable;
    private Table returnButton;
    private TextButton textButton;
    private TextButton playerSetup;
    private TextButton generalSettings;
    private TextButton videoSettings;
    private CheckBox debug;
    private CheckBox opponentModeling;
    private CheckBox opponentModeling2;
    private CheckBox fullScreen;
    private MenuView menuView;
    private Label boardSizes;
    private SelectBox<String> boardSize;
    private SelectBox<String> algorithms;
    private SelectBox<String> algorithms2;
    private SelectBox<String> player1;
    private SelectBox<String> player2;
    private String p1;
    private String p2;
    public static boolean TESTING = false;
    public static final int TEST_ITERATIONS = 10;
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
    /*
    ####################################################################################################################
                                                    Settings
    ####################################################################################################################
    */
        settings = new Table();
        settings.left().padLeft(20);

        ButtonGroup<TextButton> settingsGroup = new ButtonGroup<>();
        settingsGroup.setMaxCheckCount(1);

        generalSettings = new TextButton("General settings", skin, "menu");
        generalSettings.getLabel().setAlignment(Align.left);
        settings.add(generalSettings).width(200);
        settingsGroup.add(generalSettings);
        settings.row();

        playerSetup = new TextButton("Player-setup", skin, "menu");
        playerSetup.getLabel().setAlignment(Align.left);
        settings.add(playerSetup).fillX();
        settingsGroup.add(playerSetup);
        settings.row();

        videoSettings = new TextButton("Video settings", skin, "menu");
        videoSettings.getLabel().setAlignment(Align.left);
        settings.add(videoSettings).fillX();
        settingsGroup.add(videoSettings);
        settings.row();

    /*
    ####################################################################################################################
                                                     Title
    ####################################################################################################################
    */


        titleTable = new Table();
        titleTable.top();

        Label title = new Label("Options Menu", skin,"title");
        titleTable.add(title).padTop(200);

    /*
    ####################################################################################################################
                                                     General Settings
    ####################################################################################################################
    */

        generalSettingsTable = new Table();

        Table boardSizeTable = new Table();
        boardSizes = new Label("Board-sizes: ",skin);
        boardSizeTable.add(boardSizes).padRight(5);

        String[] boardSizeOptions = {"5x6","10x10"};
        boardSize = new SelectBox<>(skin);
        boardSize.setItems(boardSizeOptions);
        boardSizeTable.add(boardSize);

        generalSettingsTable.add(boardSizeTable).left().fillX();
        generalSettingsTable.row();

        debug = new CheckBox("Debug mode ",MainLoop.skin);
        if(Evolution.debugPrinting){
            debug.setChecked(true);
        }
        generalSettingsTable.add(debug).width(200);
        generalSettingsTable.row();


        generalSettingsTable.setVisible(showingMenu);
        if(showingMenu){
            menuDisplayed = generalSettingsTable;
        }

    /*
    ####################################################################################################################
                                                     Player Setup
    ####################################################################################################################
    */
        playerSetupTable = new Table();

        String[] content = {"Human", "AI"};
        player1 = new SelectBox<>(skin);
        player1.setItems(content);
        player2 = new SelectBox<>(skin);
        player2.setItems(content);

        playerSetupTable.add(new Label("White", skin)).fill().pad(5);
        playerSetupTable.add(new Label("Black", skin)).fill().pad(5);
        playerSetupTable.row();
        playerSetupTable.add(player1).fill().pad(5);
        playerSetupTable.add(player2).fill().pad(5);
        playerSetupTable.row();

        String[] algorithmOptions = {"Evolution", "Greedy", "Alpha-Beta", "Monte-Carlo"};
        algorithms = new SelectBox<>(skin);
        algorithms.setItems(algorithmOptions);
        playerSetupTable.add(algorithms).fill().pad(5);
        algorithms.setVisible(false);

        algorithms2 = new SelectBox<>(skin);
        algorithms2.setItems(algorithmOptions);
        playerSetupTable.add(algorithms2).fill().pad(5);
        algorithms2.setVisible(false);

        playerSetupTable.row();

        opponentModeling = new CheckBox("Opponent modeling for algorithm 1: ", skin);
        if (OPPONENT_MODELING_1) {
            opponentModeling.setChecked(true);
        }
        playerSetupTable.add(opponentModeling).fill().pad(5);
        opponentModeling.setVisible(false);

        opponentModeling2 = new CheckBox("Opponent modeling for algorithm 2: ", skin);
        if (OPPONENT_MODELING_2) {
            opponentModeling2.setChecked(true);
        }
        playerSetupTable.add(opponentModeling2).fill().pad(5);
        opponentModeling2.setVisible(false);

        playerSetupTable.row();



        playerSetupTable.setVisible(false);

    /*
    ####################################################################################################################
                                                     Video Settings
    ####################################################################################################################
    */
        videoSettingsTable = new Table();

        fullScreen = new CheckBox("Fullscreen",skin);
        videoSettingsTable.add(fullScreen);
        if(Gdx.graphics.isFullscreen()){
            fullScreen.setChecked(true);
        }

        videoSettingsTable.setVisible(false);

    /*
    ####################################################################################################################
                                                     Return button
    ####################################################################################################################
    */
        returnButton = new Table();
        returnButton.right().padRight(20);

        textButton = new TextButton("Return", skin, "menu");
        textButton.getLabel().setAlignment(Align.left);
        returnButton.add(textButton).width(200);

    /*
    ####################################################################################################################
                                                     Other stuff
    ####################################################################################################################
    */

        generalSettingsTable.setFillParent(true);
        playerSetupTable.setFillParent(true);
        videoSettingsTable.setFillParent(true);
        titleTable.setFillParent(true);
        returnButton.setFillParent(true);
        settings.setFillParent(true);

        createListeners();

        //-----------------------
        //Tmp
        stage.addActor(UIdebugger);
        //-----------------------

        stage.addActor(returnButton);
        stage.addActor(settings);
        stage.addActor(titleTable);
        stage.addActor(generalSettingsTable);
        stage.addActor(playerSetupTable);
        stage.addActor(videoSettingsTable);
    }
    private void createListeners(){

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
                        if (opponentModeling2.isChecked()) {
                            OPPONENT_MODELING_2 = true;
                        } else {
                            OPPONENT_MODELING_2 = false;
                        }
                    }
                }
                menuView.setP1(p1);
                menuView.setP2(p2);
                viewManager.pop();
            }
        });

        UIdebugger.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(debugUI){
                    debugUI = false;
                }
                else{
                    debugUI = true;
                }
                if(debugUI){
                    generalSettingsTable.debug();
                    playerSetupTable.debug();
                    videoSettingsTable.debug();
                    titleTable.debug();
                    settings.debug();
                    returnButton.debug();
                }
                else{
                    generalSettingsTable.debug(Table.Debug.none);
                    playerSetupTable.debug(Table.Debug.none);
                    videoSettingsTable.debug(Table.Debug.none);
                    titleTable.debug(Table.Debug.none);
                    settings.debug(Table.Debug.none);
                    returnButton.debug(Table.Debug.none);
                }
            }
        });

        generalSettings.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(!showingMenu) {
                    generalSettingsTable.setVisible(true);
                    showingMenu = true;
                    menuDisplayed = generalSettingsTable;
                }
                else{
                    if(menuDisplayed != null){
                        menuDisplayed.setVisible(false);
//                        if(menuDisplayed == generalSettingsTable){
//                            showingMenu = false;
//                            return;
//                        }
                    }
                    generalSettingsTable.setVisible(true);
                    menuDisplayed = generalSettingsTable;
                }
            }
        });
        playerSetup.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(!showingMenu) {
                    playerSetupTable.setVisible(true);
                    showingMenu = true;
                    menuDisplayed = playerSetupTable;
                }
                else{
                    if(menuDisplayed != null){
                        menuDisplayed.setVisible(false);
//                        if(menuDisplayed == playerSetupTable){
//                            showingMenu = false;
//                            return;
//                        }

                    }
                    playerSetupTable.setVisible(true);
                    menuDisplayed = playerSetupTable;
                }
            }
        });
        videoSettings.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(!showingMenu) {
                    videoSettingsTable.setVisible(true);
                    showingMenu = true;
                    menuDisplayed = videoSettingsTable;
                }
                else{
                    if(menuDisplayed != null){
                        menuDisplayed.setVisible(false);
//                        if(menuDisplayed == videoSettingsTable){
//                            showingMenu = false;
//                            return;
//                        }
                    }
                    videoSettingsTable.setVisible(true);
                    menuDisplayed = videoSettingsTable;
                }
            }
        });

        fullScreen.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (fullScreen.isChecked()) {

                    Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode(Gdx.graphics.getPrimaryMonitor()));


                }else {
                    Gdx.graphics.setWindowedMode(1366,768);
                }

            }
        });

        player1.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                p1 = player1.getSelected();
                if(player1.getSelected().equals("Human") && player2.getSelected().equals("Human")) {
                    algorithms.setVisible(false);
                    algorithms2.setVisible(false);
                }else if(player1.getSelected().equals("AI") && player2.getSelected().equals("AI")){
                    algorithms.setVisible(true);
                    if(algorithms.getSelected().equals("Evolution")){
                        opponentModeling.setVisible(true);
                    }
                    algorithms2.setVisible(true);
                    if(algorithms2.getSelected().equals("Evolution")){
                        opponentModeling2.setVisible(true);
                    }

                }
                else if(player1.getSelected().equals("AI")){
                    algorithms.setVisible(true);
                    if(algorithms.getSelected().equals("Evolution")){
                        opponentModeling.setVisible(true);
                    }
                }
                else if(player2.getSelected().equals("AI")){
                    algorithms.setVisible(true);
                    if(algorithms2.getSelected().equals("Evolution")){
                        opponentModeling2.setVisible(true);
                    }
                }


            }
        });

        player2.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                p2 = player2.getSelected();
                if(player1.getSelected().equals("Human") && player2.getSelected().equals("Human")) {
                    algorithms.setVisible(false);
                    algorithms2.setVisible(false);
                }else if(player1.getSelected().equals("AI") && player2.getSelected().equals("AI")){
                    algorithms.setVisible(true);
                    if(algorithms.getSelected().equals("Evolution")){
                        opponentModeling.setVisible(true);
                    }
                    algorithms2.setVisible(true);
                    if(algorithms2.getSelected().equals("Evolution")){
                        opponentModeling2.setVisible(true);
                    }

                }
                else if(player1.getSelected().equals("AI")){
                    algorithms.setVisible(true);
                    if(algorithms.getSelected().equals("Evolution")){
                        opponentModeling.setVisible(true);
                    }
                }
                else if(player2.getSelected().equals("AI")){
                    algorithms.setVisible(true);
                    if(algorithms2.getSelected().equals("Evolution")){
                        opponentModeling2.setVisible(true);
                    }
                }


            }
        });

        algorithms.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(algorithms.getSelected().equals("Evolution") && algorithms2.getSelected().equals("Evolution")){
                    opponentModeling.setVisible(true);
                    opponentModeling2.setVisible(true);
                }
                else if(algorithms.getSelected().equals("Evolution")){
                    opponentModeling.setVisible(true);
                    opponentModeling2.setVisible(false);

                }
                else if(algorithms2.getSelected().equals("Evolution")){
                    opponentModeling.setVisible(false);
                    opponentModeling2.setVisible(true);
                }
                else{
                    opponentModeling.setVisible(false);
                    opponentModeling2.setVisible(false);
                }
            }
        });

        algorithms2.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(algorithms.getSelected().equals("Evolution") && algorithms2.getSelected().equals("Evolution")){
                    opponentModeling.setVisible(true);
                    opponentModeling2.setVisible(true);
                }
                else if(algorithms.getSelected().equals("Evolution")){
                    opponentModeling.setVisible(true);
                    opponentModeling2.setVisible(false);

                }
                else if(algorithms2.getSelected().equals("Evolution")){
                    opponentModeling.setVisible(false);
                    opponentModeling2.setVisible(true);
                }
                else{
                    opponentModeling.setVisible(false);
                    opponentModeling2.setVisible(false);
                }

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
