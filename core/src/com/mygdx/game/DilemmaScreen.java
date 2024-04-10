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
    private boolean gamePaused;

    public DilemmaScreen() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        createUI(); // Create buttons when the dilemma screen is initialized
        gamePaused = true;
    }

    private void createUI() {
        // Create a skin
        Skin skin = new Skin();

        // Create a bitmap font for text
        BitmapFont font = new BitmapFont();

        // Define the background texture region for the square
        TextureRegionDrawable background = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("background.png")))); // Replace "background.png" with your desired texture

        // Create a button style for the square
        TextButton.TextButtonStyle squareStyle = new TextButton.TextButtonStyle();
        squareStyle.up = background;
        squareStyle.font = font;

        // Create a button for the square
        TextButton square = new TextButton("The ship is on fire!\nWhat do you do?", squareStyle);
        square.setSize(400, 400);
        square.setPosition(Gdx.graphics.getWidth() / 2f - 200, Gdx.graphics.getHeight() / 2f - 200);

        // Add the square to the stage
        stage.addActor(square);

        // Calculate button positions relative to the square
        float buttonX = square.getX() + 100; // Adjust the X position as needed
        float option1Y = square.getY() + 250; // Adjust the Y position as needed
        float option2Y = square.getY() + 150; // Adjust the Y position as needed

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
                gamePaused = false;
                // Resume game
                ResumeGameFunction();
                // Dispose of the screen
                DisposeFunction();
            }
        });

        option2Button = new TextButton("Option 2", skin);
        option2Button.setPosition(buttonX, option2Y);;
        option2Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Option 2 clicked");
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





      /*  Skin skin = new Skin();
       // BitmapFont font = new BitmapFont(Gdx.files.internal("path/to/font.fnt")); // Load your font file
       // skin.add("default-font", font);

        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.font = skin.getFont("default-font");
        buttonStyle.fontColor = Color.BLACK; // Set the font color

        skin.add("default", buttonStyle);

        option2Button = new TextButton("Option 2", skin);
        option2Button.setPosition(300, 200);
        stage.addActor(option2Button); // Add the button to the stage
*/

     /*   Skin skin = new Skin(); // Create a new skin
        BitmapFont font = new BitmapFont(); // Create a new font

        // Define the background texture region for the square
        TextureRegionDrawable background = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("background.png")))); // Replace "background.png" with your desired texture

        // Create a button style for the square
        TextButton.TextButtonStyle squareStyle = new TextButton.TextButtonStyle();
        squareStyle.up = background;

        // Create a button for the square
        TextButton square = new TextButton("The ship is on fire!\nWhat do you do?", squareStyle);
        square.setSize(400, 400);
        square.setPosition(Gdx.graphics.getWidth() / 2f - 200, Gdx.graphics.getHeight() / 2f - 200);

        // Add the square to the stage
        stage.addActor(square);

        // Create button style
        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.font = font;
        skin.add("default", buttonStyle);

        option1Button = new TextButton("Option 1", skin);
        option1Button.setPosition(300, 300);
        option1Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Handle option 1 choice
                gamePaused = false;
                // Resume game
                 ResumeGameFunction();
                // Dispose of the screen
                 DisposeFunction();
            }
        });

        option2Button = new TextButton("Option 2", skin);
        option2Button.setPosition(300, 200);
        option2Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Option 2 clicked");
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
    }*/

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
        update();
        // Render the stage
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();

        // Debug output for click listeners
        if (Gdx.input.justTouched()) {
            System.out.println("Click at: " + Gdx.input.getX() + ", " + Gdx.input.getY());
        }
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

    @Override
    public void dispose() {
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
}




