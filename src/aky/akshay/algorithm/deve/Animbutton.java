package aky.akshay.algorithm.deve;

import android.graphics.Matrix;
import android.view.animation.Animation;
import android.view.animation.Transformation;

 /* Class for performing animation instead of adding code in one JAVA file & making it complex.
  * Hence, This class is been generated
  * */


public class Animbutton extends Animation{

	protected void applyTransformation(float interpolatedTime,Transformation t) {
		super.applyTransformation(interpolatedTime, t);
		
		final Matrix matrix = t.getMatrix();
		matrix.setScale(interpolatedTime, interpolatedTime);
	}
}
