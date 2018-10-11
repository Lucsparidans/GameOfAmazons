package com.dke.game.Views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.dke.game.Controller.MainLoop;
import com.dke.game.Controller.ViewManager;

public class ScoreView extends View {

    private Stage stage;
    private int white;
    private int black;


    public ScoreView(ViewManager viewManager, int wScore, int bScore) {
        super(viewManager);
        white = wScore;
        black = bScore;
    }

    @Override
    public void create() {
        stage = new Stage();


        Table table = new Table();
        table.setFillParent(true);

        Label title = new Label("Scoreboard", MainLoop.skin,"title");
        table.add(title).center();
        table.row();
        Label score = new Label("White: " + white + "  Black: " + black,MainLoop.skin);
        score.setAlignment(Align.center);
        table.add(score).center();
        table.row();

        createListeners();
        //table.debugAll();
        stage.addActor(table);

    }

    private void createListeners(){
//        textButton.addListener(new ClickListener(){
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                viewManager.push(new GameView(viewManager));
//            }
//        });
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void render() {
        if (this.stage != null) {
            this.stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
            Gdx.input.setInputProcessor(stage);
            this.stage.act();
            this.stage.draw();
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
