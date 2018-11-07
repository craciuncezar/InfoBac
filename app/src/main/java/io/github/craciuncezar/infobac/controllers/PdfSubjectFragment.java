package io.github.craciuncezar.infobac.controllers;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.pdf.PdfRenderer;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.google.android.material.appbar.AppBarLayout;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import io.github.craciuncezar.infobac.BaseActivity;
import io.github.craciuncezar.infobac.viewmodels.SubjectsViewModel;

public class PdfSubjectFragment extends Fragment {
    private static final String BAREM_KEY = "ISBAREM";
    private SubsamplingScaleImageView container_pdf;
    private NestedScrollView scrollView;
    private Boolean isBarem;

    void setIsBarem(Boolean isBarem) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(BAREM_KEY, isBarem);
        this.setArguments(bundle);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        isBarem = getArguments() != null && getArguments().getBoolean(BAREM_KEY);
        SubjectsViewModel viewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(SubjectsViewModel.class);
        scrollView = new NestedScrollView(getActivity());
        scrollView.setLayoutParams(new CoordinatorLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        CoordinatorLayout.LayoutParams params =
                (CoordinatorLayout.LayoutParams) scrollView.getLayoutParams();
        params.setBehavior(new AppBarLayout.ScrollingViewBehavior());
        scrollView.requestLayout();
        scrollView.setScrollContainer(false);

        container_pdf = new SubsamplingScaleImageView(getActivity());
        container_pdf.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        scrollView.addView(container_pdf);

        viewModel.getCurrentFilePath().observe(this, this::updatePdfContainer);

        return scrollView;
    }

    private void updatePdfContainer(String filePath) {
        ArrayList<Bitmap> bitmaps = readFromPDF(isBarem ? filePath + "Barem" : filePath);
        Bitmap concatenatedBitmap = combineImageIntoOne(bitmaps);
        if (getActivity() != null && ((BaseActivity) getActivity()).getCurrentTheme().equals("Dark Theme")) {
            concatenatedBitmap = createInvertedBitmap(concatenatedBitmap);
        }
        container_pdf.setImage(ImageSource.bitmap(concatenatedBitmap));

        scrollView.fullScroll(ScrollView.FOCUS_UP);
    }

    private ArrayList<Bitmap> readFromPDF(String filePath) {
        ArrayList<Bitmap> bitmaps = new ArrayList<>();
        try {
            File temp = new File(Objects.requireNonNull(getActivity()).getCacheDir(), "tempPdf.pdf");
            FileOutputStream out = new FileOutputStream(temp);
            InputStream is = getActivity().getAssets().open(filePath + ".pdf");

            byte[] buffer = new byte[1024];
            int readBytes;
            while ((readBytes = is.read(buffer)) != -1)
                out.write(buffer, 0, readBytes);

            out.close();
            is.close();

            ParcelFileDescriptor parcelFileDescriptor = ParcelFileDescriptor.open(temp, ParcelFileDescriptor.MODE_READ_ONLY);
            PdfRenderer renderer = new PdfRenderer(parcelFileDescriptor);

            for (int i = 0; i < renderer.getPageCount(); i++) {
                PdfRenderer.Page page = renderer.openPage(i);
                int pageWidth = page.getWidth();
                int pageHeight = page.getHeight();
                float scale = Math.min((float) getActivity().getResources().getDisplayMetrics().widthPixels / pageWidth, (float) getActivity().getResources().getDisplayMetrics().heightPixels / pageHeight);
                Bitmap bitmap = Bitmap.createBitmap((int) (pageWidth * scale), (int) (pageHeight * scale), Bitmap.Config.ARGB_8888);
                page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);
                bitmaps.add(bitmap);
                page.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bitmaps;
    }

    private Bitmap combineImageIntoOne(ArrayList<Bitmap> bitmap) {
        int w = 0, h = 0;
        for (int i = 0; i < bitmap.size(); i++) {
            if (i < bitmap.size() - 1) {
                w = bitmap.get(i).getWidth() > bitmap.get(i + 1).getWidth() ? bitmap.get(i).getWidth() : bitmap.get(i + 1).getWidth();
            }
            h += bitmap.get(i).getHeight();
        }

        Bitmap temp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(temp);
        int top = 0;
        for (int i = 0; i < bitmap.size(); i++) {
            top = (i == 0 ? 0 : top + bitmap.get(i).getHeight());
            canvas.drawBitmap(bitmap.get(i), 0f, top, null);
        }
        return temp;
    }

    private Bitmap createInvertedBitmap(Bitmap src) {
        ColorMatrix colorMatrix_Inverted =
                new ColorMatrix(new float[]{
                        -1, 0, 0, 0, 255,
                        0, -1, 0, 0, 255,
                        0, 0, -1, 0, 255,
                        0, 0, 0, 1, 0});

        ColorFilter ColorFilter_Sepia = new ColorMatrixColorFilter(
                colorMatrix_Inverted);

        Bitmap bitmap = Bitmap.createBitmap(src.getWidth(), src.getHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        Paint paint = new Paint();

        paint.setColorFilter(ColorFilter_Sepia);
        canvas.drawBitmap(src, 0, 0, paint);

        return bitmap;
    }
}
