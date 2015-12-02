package com.linkedfluuuush.glowingtile.gui.touchListeners;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View.*;
import android.view.*;
import android.util.*;
import com.linkedfluuuush.glowingtile.gui.*;
import com.linkedfluuuush.glowingtile.*;
import com.linkedfluuuush.glowingtile.core.*;

public class BoardGameTouchListener implements OnTouchListener {

	private static final String TAG = BoardGameTouchListener.class.getName();
    final OnTouchListener thisListener = this;

	private MainGame mainGame;

	private float x1,x2,y1,y2;
    private boolean movedByProxi = false, movedBySwipe = false;
	private static final int MIN_DISTANCE=50;

	public BoardGameTouchListener(MainGame mainGame) {
		super();
		this.mainGame = mainGame;
	}

	@Override
	public boolean onTouch(View view, MotionEvent event) {
		final GameBoard boardView = (GameBoard) view;
		final Game game = mainGame.getGame();

        if (!movedBySwipe) {
            float x = event.getX();
            float y = event.getY();

            Log.d(TAG, "Howdy at " + game.getHowdy().getX() * 40 + "," + game.getHowdy().getY() * 40);
            Log.d(TAG, "Down with coords " + x + "," + y);

            if (x >= game.getHowdy().getX() * 40 && x <= (game.getHowdy().getX() + 1) * 40) {
                Log.d(TAG, "Same row");
                if (y <= game.getHowdy().getY() * 40 && y >= (game.getHowdy().getY() - 1) * 40) {
                    if (game.canMoveUp()) {
                        Log.d(TAG, "Go Up");
                        boardView.setOnTouchListener(null);
                        boardView.getHowdyShadeView().animate()
                            .y((game.getHowdy().getY() - 7) * 40)
                            .setDuration(100)
                            .setListener(null);

                        boardView.getHowdyView().animate()
                            .y((game.getHowdy().getY() - 1) * 40)
                            .setDuration(100)
                            .setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    super.onAnimationEnd(animation);
                                    game.moveUp();
                                    endMovement(game, boardView);
                                    boardView.setOnTouchListener(thisListener);
                                }
                            });
                    }
                    movedByProxi = true;
                } else if (y <= (game.getHowdy().getY() + 2) * 40 && y >= (game.getHowdy().getY() + 1) * 40) {
                    if(game.canMoveDown()) {
                        Log.d(TAG, "Go Down");
                        boardView.setOnTouchListener(null);
                        boardView.getHowdyShadeView().animate()
                            .y((game.getHowdy().getY() - 5) * 40)
                            .setDuration(100)
                            .setListener(null);

                        boardView.getHowdyView().animate()
                            .y((game.getHowdy().getY() + 1) * 40)
                            .setDuration(100)
                            .setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    super.onAnimationEnd(animation);
                                    game.moveDown();
                                    endMovement(game, boardView);
                                    boardView.setOnTouchListener(thisListener);
                                }
                            });

                    }
                    movedByProxi = true;
                }
            } else {
                if (y >= game.getHowdy().getY() * 40 && y <= (game.getHowdy().getY() + 1) * 40) {
                    Log.d(TAG, "Same column");
                    if (x <= game.getHowdy().getX() * 40 && x >= (game.getHowdy().getX() - 1) * 40) {
                        if(game.canMoveLeft()) {
                            Log.d(TAG, "Go Left");
                            boardView.setOnTouchListener(null);
                            boardView.getHowdyShadeView().animate()
                                .x((game.getHowdy().getX() - 7) * 40)
                                .setDuration(100)
                                .setListener(null);

                            boardView.getHowdyView().animate()
                                .x((game.getHowdy().getX()  - 1) * 40)
                                .setDuration(100)
                                .setListener(new AnimatorListenerAdapter() {
                                    @Override
                                    public void onAnimationEnd(Animator animation) {
                                        super.onAnimationEnd(animation);
                                        game.moveLeft();
                                        endMovement(game, boardView);
                                        boardView.setOnTouchListener(thisListener);
                                    }
                                });

                        }
                        movedByProxi = true;
                    } else if (x <= (game.getHowdy().getX() + 2) * 40 && x >= (game.getHowdy().getX() + 1) * 40) {
                        if(game.canMoveRight()) {
                            Log.d(TAG, "Go Right");
                            boardView.setOnTouchListener(null);
                            boardView.getHowdyShadeView().animate()
                                .x((game.getHowdy().getX() - 5) * 40)
                                .setDuration(100)
                                .setListener(null);

                            boardView.getHowdyView().animate()
                                .x((game.getHowdy().getX() + 1) * 40)
                                .setDuration(100)
                                .setListener(new AnimatorListenerAdapter() {
                                    @Override
                                    public void onAnimationEnd(Animator animation) {
                                        super.onAnimationEnd(animation);
                                        game.moveRight();
                                        endMovement(game, boardView);
                                        boardView.setOnTouchListener(thisListener);
                                    }
                                });
                        }
                        movedByProxi = true;
                    }
                }
            }
        }

		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
                if (!movedByProxi) {
                    x1 = event.getX();
                    y1 = event.getY();
                    movedBySwipe = true;
                }
				break;
			case MotionEvent.ACTION_UP:
                if (movedByProxi) {
                    Log.d(TAG, "Proximity move !");
                    movedByProxi = false;
                } else {
                    x2 = event.getX();
                    y2 = event.getY();

                    float deltaX = (x1 - x2);
                    float deltaY = (y1 - y2);

                    Log.d(TAG, "X : " + deltaX + ", Y : " + deltaY);

                    if (Math.abs(deltaX) >= MIN_DISTANCE || Math.abs(deltaY) >= MIN_DISTANCE) {
                        if (Math.abs(deltaX) > Math.abs(deltaY)) { //mvt h
                            if (deltaX < 0) {
                                //Toast.makeText(mainGame.getApplicationContext(),"Go Right",Toast.LENGTH_SHORT).show();
                                if(game.canMoveRight()) {
                                    Log.d(TAG, "Go Right");
                                    boardView.getHowdyShadeView().animate()
                                            .x((game.getHowdy().getX() - 5) * 40)
                                            .setDuration(100)
                                            .setListener(null);

                                    boardView.getHowdyView().animate()
                                            .x((game.getHowdy().getX() + 1) * 40)
                                            .setDuration(100)
                                            .setListener(new AnimatorListenerAdapter() {
                                                @Override
                                                public void onAnimationEnd(Animator animation) {
                                                    super.onAnimationEnd(animation);
                                                    game.moveRight();
                                                    endMovement(game, boardView);
                                                }
                                            });
                                }
                            } else {
                                //Toast.makeText(mainGame.getApplicationContext(),"Go Left",Toast.LENGTH_SHORT).show();
                                if(game.canMoveLeft()) {
                                    Log.d(TAG, "Go Left");
                                    boardView.getHowdyShadeView().animate()
                                            .x((game.getHowdy().getX() - 7) * 40)
                                            .setDuration(100)
                                            .setListener(null);

                                    boardView.getHowdyView().animate()
                                            .x((game.getHowdy().getX()  - 1) * 40)
                                            .setDuration(100)
                                            .setListener(new AnimatorListenerAdapter() {
                                                @Override
                                                public void onAnimationEnd(Animator animation) {
                                                    super.onAnimationEnd(animation);
                                                    game.moveLeft();
                                                    endMovement(game, boardView);
                                                }
                                            });

                                }
                            }
                        } else if (Math.abs(deltaX) < Math.abs(deltaY)) { //mvt v
                            if (deltaY < 0) {
                                //Toast.makeText(mainGame.getApplicationContext(),"Go Down",Toast.LENGTH_SHORT).show();
                                if(game.canMoveDown()) {
                                    Log.d(TAG, "Go Down");
                                    boardView.getHowdyShadeView().animate()
                                            .y((game.getHowdy().getY() - 5) * 40)
                                            .setDuration(100)
                                            .setListener(null);

                                    boardView.getHowdyView().animate()
                                            .y((game.getHowdy().getY() + 1) * 40)
                                            .setDuration(100)
                                            .setListener(new AnimatorListenerAdapter() {
                                                @Override
                                                public void onAnimationEnd(Animator animation) {
                                                    super.onAnimationEnd(animation);
                                                    game.moveDown();
                                                    endMovement(game, boardView);
                                                }
                                            });

                                }
                            } else {
                                //Toast.makeText(mainGame.getApplicationContext(),"Go Up",Toast.LENGTH_SHORT).show();
                                if (game.canMoveUp()) {
                                    Log.d(TAG, "Go Up");
                                    boardView.getHowdyShadeView().animate()
                                            .y((game.getHowdy().getY() - 7) * 40)
                                            .setDuration(100)
                                            .setListener(null);

                                    boardView.getHowdyView().animate()
                                            .y((game.getHowdy().getY() - 1) * 40)
                                            .setDuration(100)
                                            .setListener(new AnimatorListenerAdapter() {
                                                @Override
                                                public void onAnimationEnd(Animator animation) {
                                                    super.onAnimationEnd(animation);
                                                    game.moveUp();
                                                    endMovement(game, boardView);
                                                }
                                            });
                                }
                            }
                        }

                    }
                }
				break;
		}

		return true;
	}

    private void endMovement(final Game game, final GameBoard boardView){
        boardView.setGame(game);
        boardView.invalidate();
        boardView.getGlowingBoard().invalidate();

        movedBySwipe = false;

        if (game.isLost()) {
            Log.d(TAG, "Game lost !");
            
            boardView.setOnTouchListener(null);

            boardView.getHowdyView().animate()
                        .scaleX(0)
                        .scaleY(0)
                        .setDuration(500)
                        .setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                boardView.animate()
                                    .alpha(0f)
                                    .setDuration(300)
                                    .setListener(new AnimatorListenerAdapter() {
                                        @Override
                                        public void onAnimationEnd(Animator animation) {
                                            mainGame.loseGame();
                                            boardView.animate()
                                                .alpha(1f)
                                                .setDuration(300)
                                                .setListener(new AnimatorListenerAdapter(){
                                                    @Override
                                                    public void onAnimationEnd(Animator animation){
                                                        boardView.getHowdyView().setScaleX(1);
                                                        boardView.getHowdyView().setScaleY(1);
                                                        boardView.setOnTouchListener(thisListener);
                                                    }
                                                });
                                        }
                                    });
                                boardView.getGlowingBoard().animate()
                                    .alpha(0f)
                                    .setDuration(300)
                                    .setListener(new AnimatorListenerAdapter() {
                                        @Override
                                        public void onAnimationEnd(Animator animation) {
                                            boardView.getGlowingBoard().animate()
                                                .alpha(1f)
                                                .setDuration(300)
                                                .setListener(null);
                                        }
                                    });
                            }
                    });
        }

        if (game.isWon()) {
            Log.d(TAG, "Game won !");
            boardView.setOnTouchListener(null);
            boardView.animate()
                    .alpha(0f)
                    .setDuration(1000)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            mainGame.winGame();
                            boardView.animate()
                                    .alpha(1f)
                                    .setDuration(1000)
                                    .setListener(new AnimatorListenerAdapter() {
                                        @Override
                                        public void onAnimationEnd(Animator animation){
                                            boardView.setOnTouchListener(thisListener);
                                        }
                                    });
                        }
                    });

            boardView.getGlowingBoard().animate()
                    .alpha(0f)
                    .setDuration(1000)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            boardView.getGlowingBoard().animate()
                                    .alpha(1f)
                                    .setDuration(1000)
                                    .setListener(null);
                        }
                    });
        }
    }
}
