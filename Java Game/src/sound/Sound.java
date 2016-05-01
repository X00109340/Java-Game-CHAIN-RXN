 package sound;
 
 import java.applet.Applet;
 import java.applet.AudioClip;
 
 public class Sound
 {
   public static final AudioClip BALL = Applet.newAudioClip(Sound.class.getResource("/sound/ball.wav"));
   public static final AudioClip BALLCOLLISION = Applet.newAudioClip(Sound.class.getResource("/sound/coin.wav"));
   public static final AudioClip WON = Applet.newAudioClip(Sound.class.getResource("/sound/won.wav"));
   public static final AudioClip FAIL = Applet.newAudioClip(Sound.class.getResource("/sound/fail.wav"));
   public static final AudioClip LEVEL = Applet.newAudioClip(Sound.class.getResource("/sound/level.wav"));
   public static final AudioClip CLICK = Applet.newAudioClip(Sound.class.getResource("/sound/click.wav"));
   public static final AudioClip INTRO = Applet.newAudioClip(Sound.class.getResource("/sound/intro.wav"));
   public static final AudioClip CONGRATS = Applet.newAudioClip(Sound.class.getResource("/sound/congrats.wav"));
   public static final AudioClip BUTTONCLICK = Applet.newAudioClip(Sound.class.getResource("/sound/buttonClick.wav"));

 }


