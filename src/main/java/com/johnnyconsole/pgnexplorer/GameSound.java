package com.johnnyconsole.pgnexplorer;

import javafx.scene.media.AudioClip;

import java.util.Objects;

public class GameSound {

    public static final AudioClip SOUND_MOVE = new AudioClip(Objects.requireNonNull(GameSound.class.getResource("/sound/move.mp3")).toExternalForm()),
        SOUND_CAPTURE = new AudioClip(Objects.requireNonNull(GameSound.class.getResource("/sound/capture.mp3")).toExternalForm()),
        SOUND_CASTLE = new AudioClip(Objects.requireNonNull(GameSound.class.getResource("/sound/castle.mp3")).toExternalForm()),
        SOUND_CHECK = new AudioClip(Objects.requireNonNull(GameSound.class.getResource("/sound/check.mp3")).toExternalForm()),
        SOUND_PROMOTE = new AudioClip(Objects.requireNonNull(GameSound.class.getResource("/sound/promote.mp3")).toExternalForm()),
        SOUND_GAME_END = new AudioClip(Objects.requireNonNull(GameSound.class.getResource("/sound/game-end.mp3")).toExternalForm());
}
