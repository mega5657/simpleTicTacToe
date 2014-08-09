/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.womp.simpletictactoe;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

/**
 *
 * @author Atog
 */
public class GameScreen implements Screen {
//X is equal to 1 O is equal to 2
//0 is player1, 1 is player2

    TicTacToe game;
    OrthographicCamera cam;
    final float DEVICE_RATIO = (Gdx.graphics.getWidth() / Gdx.graphics.getHeight());
    final float DEVICE_AREA = (Gdx.graphics.getWidth() * Gdx.graphics.getHeight());
    final float DEVICE_WIDTH = (Gdx.graphics.getWidth());
    final int DEVICE_HEIGHT = (Gdx.graphics.getHeight());

    final float VIRTUAL_WIDTH = 720;
    final float VIRTUAL_HEIGHT = 1280;
    final float VIRTUAL_AREA = 720 * 1280;

    final float SCALE = VIRTUAL_AREA / DEVICE_AREA;

    final int VIEWPORT_WIDTH = 720;
    final int VIEWPORT_HEIGHT = 1280;

    final int ASPECT_RATIO = (VIEWPORT_WIDTH / VIEWPORT_HEIGHT);
    // final float boxSize = DEVICE_AREA / 5625;
    //   final float boxSize = DEVICE_AREA * (200*200 / VIRTUAL_AREA);
    final float boxSize = (float) Math.sqrt((DEVICE_AREA * (200 * 200 / VIRTUAL_AREA)));

    int viewportX, viewportY, viewportWidth, viewportHeight;
    Rectangle viewport;
    SpriteBatch batcher;
    ShapeRenderer rend;
    String currentPlayer = "Player 1";
    InputHandler inputHandler;

    boolean winner = false;
    boolean tie = false;

    int[] ticTacGrid;
    int turnCount = 0;
    int check = 0;
    int winningType = 0;
    int winningMethod = 10;
    int mousePosX = 0, mousePosY = 0;
    int player1Win = 0, player2Win = 0;
    float stateTime = 0;

    Texture restartPng;
    Texture backPng;
    Vector3 touchPos;
    Grid[] gridBounds;
    Rectangle resetBounds, backBounds;
    Music placement;

    BitmapFont font15;
    BitmapFont font22;

    //      6  7  8
    //      3  4  5
    //      0  1  2
    public GameScreen(TicTacToe game) {

        this.game = game;
        batcher = new SpriteBatch();
        cam = new OrthographicCamera(DEVICE_WIDTH, DEVICE_HEIGHT);

        cam.position.set(DEVICE_WIDTH / 2, DEVICE_HEIGHT / 2, 0);

        ticTacGrid = new int[9];
        gridBounds = new Grid[9];
        rend = new ShapeRenderer();

        initializeGrid();

        inputHandler = new InputHandler();

        Gdx.input.setInputProcessor(inputHandler);

        placement = Gdx.audio.newMusic(Gdx.files.internal("data/Hit_Hurt.wav"));

        resetBounds = new Rectangle(365, 420, 40, 40);
        backBounds = new Rectangle(5, 420, 40, 40);
        //0 = small 400 x 854
        //1 = medium 720 x 1280
        //2 = large 1080 x 1920
        //3 original 400x450

        Gdx.gl20.glLineWidth(10);

    }

    @Override
    public void render(float deltaTime) {
            //    game.markingFont.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);

        //original 400x450 = 180,000
        //5625
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //  Gdx.gl.glViewport(viewportX, viewportY, viewportWidth, viewportHeight);
        stateTime += deltaTime;
        cam.update();
        batcher.setProjectionMatrix(cam.combined);
        rend.begin(ShapeRenderer.ShapeType.Line);

        //0 = small 400 x 854
        //1 = medium 720 x 1280
        //2 = large 1080 x 1920
        //3 original
        if (game.debug) {
            rend.setColor(Color.RED);
            renderGrid();
            rend.setColor(Color.WHITE);
        }

        rend.line(gridBounds[0].bounds.x + boxSize, gridBounds[0].bounds.y - 2, gridBounds[0].bounds.x + boxSize, gridBounds[0].bounds.y + boxSize * 3 + 2);
        rend.line(gridBounds[0].bounds.x + boxSize * 2, gridBounds[0].bounds.y - 2, gridBounds[0].bounds.x + boxSize * 2, gridBounds[0].bounds.y + boxSize * 3 + 2);
// vertical line 1 and 2
        rend.line(gridBounds[0].bounds.x - 2, gridBounds[0].bounds.y + boxSize * 2, gridBounds[0].bounds.x + boxSize * 3 + 2, gridBounds[0].bounds.y + boxSize * 2);
        rend.line(gridBounds[0].bounds.x - 2, gridBounds[0].bounds.y + boxSize, gridBounds[0].bounds.x + boxSize * 3 + 2, gridBounds[0].bounds.y + boxSize);
// horizontal line 1 and 2
        rend.end();

        batcher.begin();
        drawMarks();
        game.tictactoeFont.setColor(game.settingsScreen.getFontColor());
       // game.tictactoeFont.draw(batcher, "Player 1: " + player1Win + "", 0, 0);
        // game.tictactoeFont.draw(batcher, "Player 2: " + player2Win + "", 0, 0);

        if (!tie) {
            if (winner) {
                drawWinningLine(rend);
                if (winningType == 1) {
                    //  game.tictactoeFont.draw(batcher, "Player 1 Wins", 140, 375);
                    player1Win++;
                } else {
               //     game.tictactoeFont.draw(batcher, "Player 2 Wins", 140, 375);
                    // player2Win++;
                } //end else winningType

            } else {
                //    game.tictactoeFont.draw(batcher, currentPlayer, 158, 375);
            } // end else winner
        }//end if not tie
        else {
            //   game.tictactoeFont.draw(batcher, "Tie.", 165, 375);
        } // end else tie
        game.symbolsFont.setColor(game.settingsScreen.getGridColor());
       // game.symbolsFont.draw(batcher, "G", 365, 450);
        //restart arrow
        // game.symbolsFont.draw(batcher, "W", 0, 450);
        //back arrow

        batcher.end();
        if (stateTime > 1) {
            update();
        }

        inputHandler.update();

        //  renderSmall();
    }

    private void renderLarge(Float deltaTime) {
//        {
//
//            cam.update();
//            Gdx.gl.glViewport(viewportX, viewportY, viewportWidth, viewportHeight);
//
//            //  Gdx.gl.glViewport(viewport.x, viewport.y, viewport.width, viewport.height);
//            batcher.setProjectionMatrix(cam.combined);
//            rend.begin(ShapeRenderer.ShapeType.Filled);
//            rend.setColor(game.settingsScreen.getBackColor());
//            rend.rect(0, 0, game.DEVICE_WIDTH, game.DEVICE_HEIGHT);
//            rend.setColor(game.settingsScreen.getGridColor());
//
//            rend.rect(155, 60, 5, 270); //vertical line1
//            rend.rect(245, 60, 5, 270); //vertical line2
//            rend.rect(65, 150, 270, 5); //horizontal line1
//            rend.rect(65, 240, 270, 5); //horizontal line2
//
//            //draw tictactoe grid
//            rend.end();
//            rend.begin(ShapeRenderer.ShapeType.Line);
//            // rend.rect(65, 60, 270, 270);
//            // rend.rect(resetBounds.x, resetBounds.y, resetBounds.width, resetBounds.height);
//            //  rend.rect(backBounds.x, backBounds.y, backBounds.width, backBounds.height);
//            //rend.rect(resetBounds.x, resetBounds.y, resetBounds.width, resetBounds.height);
//            rend.end();
//
//            // renderGrid();
//            batcher.begin();
//            batcher.enableBlending();
//
//            if (inputHandler.getClickedPosX() != 0) {
//                mousePosX = inputHandler.getClickedPosX();
//                mousePosY = inputHandler.getClickedPosY();
//                System.out.println("X: " + inputHandler.getClickedPosX() + " Y:" + inputHandler.getClickedPosY() + "");
//            }
//            //   System.out.println(inputHandler.getClickedPosX());
//
//            if (TicTacToe.debug) {
//                font.draw(batcher, "Mouse Position: " + mousePosX + ", " + mousePosY + "", 25, 425);
//                font.draw(batcher, "Turn Count:" + turnCount + "", 25, 440);
//            }
//            font.setColor(game.settingsScreen.getFontColor());
//            font.draw(batcher, "Player 1: " + player1Win + "", 0, 0);
//            font.draw(batcher, "Player 2: " + player2Win + "", 0, 0);
//
//            if (!tie) {
//                if (winner) {
//                    if (winningType == 1) {
//                        font.draw(batcher, "Player 1 Wins", 140, 375);
//                        player1Win++;
//                    } else {
//                        font.draw(batcher, "Player 2 Wins", 140, 375);
//                        player2Win++;
//                    } //end else winningType
//
//                } else {
//                    font.draw(batcher, currentPlayer, 158, 375);
//                } // end else winner
//            }//end if not tie
//            else {
//                font.draw(batcher, "Tie.", 165, 375);
//            } // end else tie
//            symbolsFont.setColor(game.settingsScreen.getGridColor());
//            symbolsFont.draw(batcher, "G", 365, 450);
//            //restart arrow
//            symbolsFont.draw(batcher, "W", 0, 450);
//            //back arrow
//            batcher.end();
//
//            batcher.begin();
//            drawMarks();
//            batcher.end();
//            drawWinningLine(rend);
//            stateTime += deltaTime;
//            if (stateTime > 1) {
//                update();
//            }
//
//            inputHandler.update();
//
//        }
    }

    @Override
    public void resize(int width, int height) {
//        int aspectRatio = width / height;
//        int scale = 1;
//        int cropX = 0, cropY = 0;
//
//        if (aspectRatio > ASPECT_RATIO) {
//            scale = height / VIEWPORT_HEIGHT;
//            cropX = (width - VIEWPORT_WIDTH / scale) / 2;
//
//        } else if (aspectRatio < ASPECT_RATIO) {
//            scale = width / VIEWPORT_WIDTH;
//        } else {
//            scale = width / VIEWPORT_WIDTH;
//        }
//
//        int w = VIEWPORT_WIDTH / scale;
//        int h = VIEWPORT_HEIGHT / scale;
//        viewportX = cropX;
//        viewportY = cropY;
//        viewportWidth = w;
//        viewportHeight = h;

    }

    @Override
    public void show() {
        Gdx.gl20.glLineWidth(10);
        cam.position.set(DEVICE_WIDTH / 2, DEVICE_HEIGHT / 2, 0);
        Gdx.input.setInputProcessor(inputHandler);
        player1Win = 0;
        player2Win = 0;

    }

    @Override
    public void hide() {
        reset();
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

    public void update() {
        if (!winner) {
            if (inputHandler.getClickedPosX() != 0) {
                for (int n = 0; n < 9; n++) {
                    checkIfGridTouched(gridBounds[n]);

                } //end for loop
            }//end if statement
            checkForWinner();
        }//end if not winner 

        if (CollisionTester.pointInRectangle(resetBounds, inputHandler.getClickedPosX(), inputHandler.getClickedPosY())) {
            reset();
        }// end reset button

        if (CollisionTester.pointInRectangle(backBounds, inputHandler.getClickedPosX(), inputHandler.getClickedPosY())) {
            reset();
            game.setScreen(game.settingsScreen);
        }
    } //end of update method

    private void checkIfGridTouched(Grid grid) {
        if (!grid.marked) {
            if (CollisionTester.pointInRectangle(grid.bounds, inputHandler.getClickedPosX(), inputHandler.getClickedPosY())) {
                if (turnCount % 2 == 0) {
                    currentPlayer = "Player 2";
                    grid.mark(1);
                    placement.play();
                }
                if (turnCount % 2 != 0) {
                    currentPlayer = "Player 1";
                    grid.mark(2);
                    placement.play();
                }
                turnCount++;
            } //end collision tester method
        }
    } //end checkGridTouched method

    private void checkForWinner() {
        //bottom horizontal = 0
        //middle horizontal = 1
        //top horizontal = 2
        // left vertical = 3
        // middle vertical = 4
        // right vertical = 5
        // top left diagonal = 6
        // top right diagonal = 7
        if (turnCount >= 5) {
            int currentType = 3;
            //check for horizontal winners
            for (int n = 0; n < 7; n += 3) {
                currentType = 3;
                if (gridBounds[n].type != 0) {
                    currentType = gridBounds[n].type;
                    if (gridBounds[n + 1].type == currentType) {
                        if (gridBounds[n + 2].type == currentType) {
                            if (n == 0) {
                                winningMethod = 0;
                            }
                            if (n == 3) {
                                winningMethod = 1;
                            }
                            if (n == 6) {
                                winningMethod = 2;
                            }
                            win(currentType);
                            return;
                        } //end if n+2 type equals currentype
                    } //end if n+1.type equals currentType
                } //end if type!= 2
            }//check for horizontal winners

            //check for vertical winners
            for (int n = 0; n < 3; n++) {
                currentType = 3;
                if (gridBounds[n].type != 0) {
                    currentType = gridBounds[n].type;
                    if (gridBounds[n + 3].type == currentType) {
                        if (gridBounds[n + 6].type == currentType) {
                            if (n == 0) {
                                winningMethod = 3;
                            }
                            if (n == 1) {
                                winningMethod = 4;
                            }
                            if (n == 2) {
                                winningMethod = 5;
                            }
                            win(currentType);
                            return;
                        }
                    }
                }
            }//check for vertical winners

            //check for top right diagonal
            if (gridBounds[0].type == gridBounds[4].type) {
                currentType = 3;

                if (gridBounds[0].type == gridBounds[8].type) {
                    currentType = gridBounds[0].type;
                    win(currentType);
                    winningMethod = 6;

                    return;
                }
            }//check for top right diagonal

            //check for top left diagonal
            if (gridBounds[2].type == gridBounds[4].type) {
                currentType = 3;
                if (gridBounds[2].type == gridBounds[6].type) {
                    currentType = gridBounds[2].type;
                    win(currentType);
                    winningMethod = 7;

                    return;
                } // end if gb 2 is equal to 8
            } // end if gb2 is equal to gb 4
            //check for top left diagonal
        }//end if turncount is greater or equal to 5

        if (turnCount == 9 && !winner) {
            System.out.println("there is no winner");
            tie = true;
        }
    } // end checkForWinner, 

    private void initializeGrid() {
        //  gridBounds[0] = new Grid(DEVICE_AREA * (50 / VIRTUAL_AREA), VIRTUAL_HEIGHT, boxSize, boxSize);
        gridBounds[0] = new Grid((DEVICE_WIDTH * (50 / VIRTUAL_WIDTH)), (DEVICE_HEIGHT * (260 / VIRTUAL_HEIGHT)), boxSize, boxSize);
        gridBounds[1] = new Grid((DEVICE_WIDTH * (50 / VIRTUAL_WIDTH)) + boxSize, (DEVICE_HEIGHT * (260 / VIRTUAL_HEIGHT)), boxSize, boxSize);
        gridBounds[2] = new Grid((DEVICE_WIDTH * (50 / VIRTUAL_WIDTH)) + boxSize * 2, (DEVICE_HEIGHT * (260 / VIRTUAL_HEIGHT)), boxSize, boxSize);

        gridBounds[3] = new Grid((DEVICE_WIDTH * (50 / VIRTUAL_WIDTH)), (DEVICE_HEIGHT * (260 / VIRTUAL_HEIGHT)) + boxSize, boxSize, boxSize);
        gridBounds[4] = new Grid((DEVICE_WIDTH * (50 / VIRTUAL_WIDTH)) + boxSize, (DEVICE_HEIGHT * (260 / VIRTUAL_HEIGHT)) + boxSize, boxSize, boxSize);
        gridBounds[5] = new Grid((DEVICE_WIDTH * (50 / VIRTUAL_WIDTH)) + boxSize * 2, (DEVICE_HEIGHT * (260 / VIRTUAL_HEIGHT)) + boxSize, boxSize, boxSize);

        gridBounds[6] = new Grid((DEVICE_WIDTH * (50 / VIRTUAL_WIDTH)), (DEVICE_HEIGHT * (260 / VIRTUAL_HEIGHT)) + boxSize * 2, boxSize, boxSize);
        gridBounds[7] = new Grid((DEVICE_WIDTH * (50 / VIRTUAL_WIDTH)) + boxSize, (DEVICE_HEIGHT * (260 / VIRTUAL_HEIGHT)) + boxSize * 2, boxSize, boxSize);
        gridBounds[8] = new Grid((DEVICE_WIDTH * (50 / VIRTUAL_WIDTH)) + boxSize * 2, (DEVICE_HEIGHT * (260 / VIRTUAL_HEIGHT)) + boxSize * 2, boxSize, boxSize);

        if (game.debug) {
            System.out.println("X: " + (DEVICE_WIDTH * (50 / VIRTUAL_WIDTH)) + "");
            System.out.println("Y: " + (DEVICE_HEIGHT * (260 / VIRTUAL_HEIGHT)) + "");
            System.out.println("BoxSize: " + boxSize + "");
            System.out.println("DEVICE_WIDTH " + DEVICE_WIDTH + "");
            System.out.println("VIRTUAL WIDTH " + VIRTUAL_WIDTH + "");
        }
    }

    public void renderGrid() {
        for (int n = 0; n < 9; n++) {
            rend.rect(gridBounds[n].bounds.x, gridBounds[n].bounds.y, gridBounds[n].bounds.width, gridBounds[n].bounds.height);
        }
    }

    private void drawMarks() {

        for (int n = 0; n < 9; n++) {
            if (gridBounds[n].marked && gridBounds[n].type == 1) {
                game.markingFont.setColor(this.game.settingsScreen.getXColor());
                //game.markingFont.draw(batcher, "X", gridBounds[n].bounds.x + ((14/400)*DEVICE_WIDTH), gridBounds[n].bounds.y + ((87/450)*DEVICE_HEIGHT));
                game.markingFont.draw(batcher, "X", gridBounds[n].bounds.x + 20, gridBounds[n].bounds.y + 180);

            } // draw X if marked X

            if (gridBounds[n].marked && gridBounds[n].type == 2) {
                game.markingFont.setColor(this.game.settingsScreen.getOColor());
//                game.markingFont.draw(batcher, "O", gridBounds[n].bounds.x + 9, gridBounds[n].bounds.y + 87);
                game.markingFont.draw(batcher, "O", gridBounds[n].bounds.x, gridBounds[n].bounds.y);

            } // draw O if marked O
        } // end For loop
    } // end drawMarks

    private void win(int currentType) {
        winner = true;
        tie = false;
        winningType = currentType;
    }

    private void drawWinningLine(ShapeRenderer rendy) {
        //bottom horizontal = 0
        //middle horizontal = 1
        //top horizontal = 2
        // left vertical = 3
        // middle vertical = 4
        // right vertical = 5
        // top right diagonal = 6
        // top left diagonal = 7

        rendy.begin(ShapeRenderer.ShapeType.Line);
        if (winningMethod == 0) {
            rendy.line(65, 105, 335, 105);
            System.out.println("drawdrawdraw");
        } //end winningtype0
        if (winningMethod == 1) {
            rendy.line(65, 195, 335, 195);
        } //end winningMethod1
        if (winningMethod == 2) {
            rendy.line(65, 285, 335, 285);
        } //end winningMethod2
        if (winningMethod == 3) {
            rendy.line(65 + 48, 60, 65 + 48, 330);

        } //end winningMethod3
        if (winningMethod == 4) {
            rendy.line(155 + 48, 60, 155 + 48, 330);

        } //end winningMethod4
        if (winningMethod == 5) {
            rendy.line(245 + 48, 60, 245 + 48, 330);

        } //end winningMethod5
        if (winningMethod == 6) {
            rendy.line(70, 65, 330, 325);
//        gridBounds[8] = new Grid(245, 240, 90, 90);

        } //end winningMethod6
        if (winningMethod == 7) {
            rendy.line(70, 325, 330, 65);
        } //end winningMethod7
        rendy.end();
    }//draw winning line method

    private void reset() {
        for (int n = 0; n < 9; n++) {
            gridBounds[n].marked = false;
            gridBounds[n].type = 0;
        } //reset grid markings

        tie = false;
        winner = false;
        turnCount = 0;
        winningType = 0;
        winningMethod = 10;
        stateTime = 0;
        currentPlayer = "Player 1";
        System.out.println("reset.");
    } //reset all variables for a new game
}
