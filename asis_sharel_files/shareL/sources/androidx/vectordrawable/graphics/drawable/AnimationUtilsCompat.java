package androidx.vectordrawable.graphics.drawable;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.content.res.Resources.Theme;
import android.content.res.XmlResourceParser;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.util.Xml;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnimationUtils;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.CycleInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import androidx.annotation.RestrictTo;
import androidx.annotation.RestrictTo.Scope;
import androidx.interpolator.view.animation.FastOutLinearInInterpolator;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

@RestrictTo({Scope.LIBRARY_GROUP_PREFIX})
public class AnimationUtilsCompat {
    public static Interpolator loadInterpolator(Context context, int i) throws NotFoundException {
        if (VERSION.SDK_INT >= 21) {
            return AnimationUtils.loadInterpolator(context, i);
        }
        XmlResourceParser xmlResourceParser = null;
        String str = "Can't load animation resource ID #0x";
        if (i == 17563663) {
            try {
                return new FastOutLinearInInterpolator();
            } catch (XmlPullParserException e) {
                StringBuilder sb = new StringBuilder();
                sb.append(str);
                sb.append(Integer.toHexString(i));
                NotFoundException notFoundException = new NotFoundException(sb.toString());
                notFoundException.initCause(e);
                throw notFoundException;
            } catch (IOException e2) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append(str);
                sb2.append(Integer.toHexString(i));
                NotFoundException notFoundException2 = new NotFoundException(sb2.toString());
                notFoundException2.initCause(e2);
                throw notFoundException2;
            } catch (Throwable th) {
                if (xmlResourceParser != null) {
                    xmlResourceParser.close();
                }
                throw th;
            }
        } else if (i == 17563661) {
            return new FastOutSlowInInterpolator();
        } else {
            if (i == 17563662) {
                return new LinearOutSlowInInterpolator();
            }
            XmlResourceParser animation = context.getResources().getAnimation(i);
            Interpolator createInterpolatorFromXml = createInterpolatorFromXml(context, context.getResources(), context.getTheme(), animation);
            if (animation != null) {
                animation.close();
            }
            return createInterpolatorFromXml;
        }
    }

    private static Interpolator createInterpolatorFromXml(Context context, Resources resources, Theme theme, XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        Interpolator pathInterpolatorCompat;
        int depth = xmlPullParser.getDepth();
        Interpolator interpolator = null;
        while (true) {
            int next = xmlPullParser.next();
            if ((next != 3 || xmlPullParser.getDepth() > depth) && next != 1) {
                if (next == 2) {
                    AttributeSet asAttributeSet = Xml.asAttributeSet(xmlPullParser);
                    String name = xmlPullParser.getName();
                    if (name.equals("linearInterpolator")) {
                        interpolator = new LinearInterpolator();
                    } else {
                        if (name.equals("accelerateInterpolator")) {
                            pathInterpolatorCompat = new AccelerateInterpolator(context, asAttributeSet);
                        } else if (name.equals("decelerateInterpolator")) {
                            pathInterpolatorCompat = new DecelerateInterpolator(context, asAttributeSet);
                        } else if (name.equals("accelerateDecelerateInterpolator")) {
                            interpolator = new AccelerateDecelerateInterpolator();
                        } else if (name.equals("cycleInterpolator")) {
                            pathInterpolatorCompat = new CycleInterpolator(context, asAttributeSet);
                        } else if (name.equals("anticipateInterpolator")) {
                            pathInterpolatorCompat = new AnticipateInterpolator(context, asAttributeSet);
                        } else if (name.equals("overshootInterpolator")) {
                            pathInterpolatorCompat = new OvershootInterpolator(context, asAttributeSet);
                        } else if (name.equals("anticipateOvershootInterpolator")) {
                            pathInterpolatorCompat = new AnticipateOvershootInterpolator(context, asAttributeSet);
                        } else if (name.equals("bounceInterpolator")) {
                            interpolator = new BounceInterpolator();
                        } else if (name.equals("pathInterpolator")) {
                            pathInterpolatorCompat = new PathInterpolatorCompat(context, asAttributeSet, xmlPullParser);
                        } else {
                            StringBuilder sb = new StringBuilder();
                            sb.append("Unknown interpolator name: ");
                            sb.append(xmlPullParser.getName());
                            throw new RuntimeException(sb.toString());
                        }
                        interpolator = pathInterpolatorCompat;
                    }
                }
            }
        }
        return interpolator;
    }

    private AnimationUtilsCompat() {
    }
}
