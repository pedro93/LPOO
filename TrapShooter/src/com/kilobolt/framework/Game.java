package com.kilobolt.framework;

import android.app.Activity;
import android.widget.EditText;

public interface Game {

    public Audio getAudio();

    public Input getInput();

    public FileIO getFileIO();

    public Graphics getGraphics();

    public void setScreen(Screen screen);

    public Screen getCurrentScreen();

    public Screen getInitScreen();

	Activity getActivity();

}
