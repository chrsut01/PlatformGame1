package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.graphics.Color;
public class DilemmaScreen extends ScreenAdapter {
    private Stage stage;
    private TextButton option1Button;
    private TextButton option2Button;
    private boolean gamePaused;

    public DilemmaScreen() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        createButtons(); // Create buttons when the dilemma screen is initialized
        gamePaused = true;
    }

    private void createButtons() {
        Skin skin = new Skin(); // Create a new skin
        BitmapFont font = new BitmapFont(); // Create a new font

        // Create button style
        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.font = font;
        skin.add("default", buttonStyle);

        option1Button = new TextButton("Option 1", skin);
        option1Button.setPosition(100, 200);
        option1Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Handle option 1 choice
                gamePaused = false;
                // Resume game
                // ResumeGameFunction();
                // Dispose of the screen
                // DisposeFunction();
            }
        });

        option2Button = new TextButton("Option 2", skin);
        option2Button.setPosition(300, 200);
        option2Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Handle option 2 choice
                gamePaused = false;
                // Resume game;
                 ResumeGameFunction();
               //  Dispose of the screen
                 DisposeFunction();
            }
        });

        stage.addActor(option1Button);
        stage.addActor(option2Button);
    }

    private void DisposeFunction() {
        // Dispose of the screen
        dispose();
    }

    private void ResumeGameFunction() {
        // Resume the game
        gamePaused = false;
    }

    @Override
    public void render(float delta) {
        // Render the stage
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void pause() {
        // Pause the game
        if (gamePaused) {
            // PauseGameFunction();
        }
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    public void showDilemmaScreen() {
        createButtons(); // Call the method to create buttons again to reset the screen
        Gdx.input.setInputProcessor(stage); // Ensure that the input processor is set to the stage
    }

    public void addToStage(Actor actor) {
        stage.addActor(actor);
    }
}




