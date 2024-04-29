package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.graphics.Color;
public class DilemmaScreen extends ScreenAdapter {
    private Stage stage;
    private TextButton option1Button;
    private TextButton option2Button;
    private TextButton square;
    private TextureRegionDrawable background;
    private Skin skin;
    private boolean gamePaused;
    private boolean buttonClicked;

    public DilemmaScreen() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        createUI(); // Create buttons when the dilemma screen is initialized
        gamePaused = true;
        buttonClicked = false;
    }

    private void createUI() {
        // Create a skin
        skin = new Skin();

        // Create a bitmap font for text
        BitmapFont font = new BitmapFont();

        // Define the background texture region for the square
        background = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("background.png")))); // Replace "background.png" with your desired texture

        // Create a button style for the square
        TextButton.TextButtonStyle squareStyle = new TextButton.TextButtonStyle();
        squareStyle.up = background;
        squareStyle.font = font;

        // Create a button for the square
        square = new TextButton("DILEMMA 1 \n Question", squareStyle);
        square.setSize(400, 400);
        square.setPosition(Gdx.graphics.getWidth() / 2f - 200, Gdx.graphics.getHeight() / 2f - 200);

        // Add the square to the stage
        stage.addActor(square);

        // Calculate button positions relative to the square
        float buttonX = square.getX() + 100; // Adjust the X position as needed
        float option1Y = square.getY() + 100; // Adjust the Y position as needed
        float option2Y = square.getY() + 50; // Adjust the Y position as needed

        // Create button style
        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.font = font;
        skin.add("default", buttonStyle);

        option1Button = new TextButton("Option 1", skin);
        option1Button.setPosition(buttonX, option1Y);
        option1Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Handle option 1 choice
                buttonClicked = true;
                gamePaused = false;
                dispose();
                // Dispose of the screen
                DisposeFunction();
                // Resume game
                ResumeGameFunction();

            }
        });

        option2Button = new TextButton("Option 2", skin);
        option2Button.setPosition(buttonX, option2Y);;
        option2Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Option 2 clicked");
                // Handle option 2 choice
                buttonClicked = true;
                gamePaused = false;

                dispose();
                //  Dispose of the screen
             //   DisposeFunction();
            /*    // Resume game;
                ResumeGameFunction();
*/
            }
        });

        stage.addActor(option1Button);
        stage.addActor(option2Button);
    }


    private void ResumeGameFunction() {
        // Resume the game
        gamePaused = false;
    }

    @Override
    public void render(float delta) {
        update();
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void pause() {
        // Pause the game
        if (gamePaused) {
            PauseGameFunction();
        }
    }

    private void PauseGameFunction() {
        // Pause the game
        gamePaused = true;
    }

    private void DisposeFunction() {
        // Dispose of the screen
        dispose();
    }
    @Override
    public void dispose() {
        background.getRegion().getTexture().dispose();
        square.clear();
        option1Button.clear();
        option2Button.clear();
        stage.dispose();
    }

    public void showDilemmaScreen() {
        createUI(); // Call the method to create buttons again to reset the screen
        Gdx.input.setInputProcessor(stage); // Ensure that the input processor is set to the stage
    }

    public void addToStage(Actor actor) {
        stage.addActor(actor);
    }
    public void update(){
        stage.act();
    }

    public boolean isButtonClicked() {
        return buttonClicked;
    }

    public int getSelectedOption() {
        if (option1Button.isChecked()) {
            return 1;
        } else if (option2Button.isChecked()) {
            return 2;
        } else {
            return 0;
        }
    }

    public void setButtonClicked(boolean b) {
        buttonClicked = b;
    }
}