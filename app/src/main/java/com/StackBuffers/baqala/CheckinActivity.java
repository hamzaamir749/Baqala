package com.StackBuffers.baqala;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.text.Html;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.StackBuffers.baqala.Adapters.CustomAdapterRecycler;
import com.StackBuffers.baqala.Adapters.StepThreeAdapter;
import com.StackBuffers.baqala.HelperClasses.AppHelper;
import com.StackBuffers.baqala.HelperClasses.GeneralData;
import com.StackBuffers.baqala.HelperClasses.Session;
import com.StackBuffers.baqala.HelperClasses.SharedHelper;
import com.StackBuffers.baqala.HelperClasses.UserSessionManager;
import com.StackBuffers.baqala.HelperClasses.VolleyMultipartRequest;
import com.StackBuffers.baqala.ModelClasses.CategoryNameStep;
import com.StackBuffers.baqala.ModelClasses.CheckinStepsModel1;
import com.StackBuffers.baqala.ModelClasses.CheckinStepsModel2;
import com.StackBuffers.baqala.ModelClasses.ClientCategoryList;
import com.StackBuffers.baqala.ModelClasses.ClientNameStep;
import com.StackBuffers.baqala.ModelClasses.StepThreeList;
import com.StackBuffers.baqala.ModelClasses.StepThreeModel;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.jaredrummler.materialspinner.MaterialSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import libs.mjn.prettydialog.PrettyDialog;
import libs.mjn.prettydialog.PrettyDialogCallback;

public class CheckinActivity extends AppCompatActivity {
    String popText = "";
    Spinner spinnerClient, spinnerCategory;
    List<ClientNameStep> clientList;
    List<CategoryNameStep> categoryList;
    ClientNameStep selectedClient;
    CategoryNameStep selectedCategory;
    Chronometer mChronometer;
    long timeWhenStopped = 0;
    ImageView scanImage;
    ProgressDialog pd;
    private static final String TAG = "CheckinActivity";
    String SelectedText = "";
    RecyclerView gridView;
    RelativeLayout relativeLayout0, relativeLayout1, relativeLayout2, relativeLayout3, relativeLayout4, relativeLayout5, relativeLayout3a;
    LinearLayout linearLayout0, linearLayout1, linearLayout2, linearLayout3;
    Button btnnext4, btnnext5, btnpadd;
    Button addproduct;
    FloatingActionButton murshidt;
    Uri uri;
    RatingBar ratingBar;
    public static EditText clientcomments, compititorsdata, pname, pcname;
    RadioGroup rgavailableonshelf, rgnearexpiry, rgstockcount, rgshelfspace;
    RadioButton proAvialbleshelfyes, proAvialbleshelfno, pNearexpiryyes, pNearexpiryno, pStockcountoverstock, pStockcountoutofstock, pShelfspaceyes, pShelfspaceno;
    float clientrating;
    ImageView qrcode, adbimg1, adbimg2, adbimg3, adbimg4, adbimg5, adbimg6, adbimg7, adafimg1, adafimg2, adafimg3, adafimg4, adafimg5, adafimg6, adafimg7, comp_img1, comp_img2, comp_img3, adbimg8, adbimg9, adbimg10, adbimg11, adafimg8, adafimg9, adafimg10, adafimg11;
    String add_bimg1, add_bimg2, add_bimg3, add_bimg4, add_bimg5, add_bimg6, add_bimg7, add_afimg1, add_afimg2, add_afimg3, add_afimg4, add_afimg5, add_afimg6, add_afimg7, client_comment, compititor_data, comp_pic1, comp_pic2, comp_pic3;
    Bitmap bitmap, bitmap1, bitmap2, bitmap3, bitmap4, bitmap5, bitmap6, bitmap7, bitmap8, bitmap9, bitmap10, bitmap11, bitmap12, bitmap13, bitmap14, bitmap15, bitmap16, bitmap17, bitmap18, bitmap19, bitmap20, bitmap21, bitmap22, bitmap23, bitmap24, bitmap25;
    boolean cc_image1 = false, cc_image2 = false, cc_image3 = false;
    CheckinStepsModel2 model2;
    List<CheckinStepsModel1> listmodel1;
    List<CheckinStepsModel2> listmodel2;
    Context context;
    CustomAdapterRecycler customAdapter;
    SharedHelper sharedHelper;
    String routeid, list2;
    Session session;
    UserSessionManager userSessionManager;
    int id;
    ProgressDialog progressDialog;
    Gson gson;
    List<Bitmap> listImages;
    boolean isBack = false;
    MaterialSpinner materialSpinner;
    List<StepThreeModel> listProducts;
    Button btnskip4, btnskip5;
    boolean ai_image1 = false, ai_image2 = false, ai_image3 = false, ai_image4 = false, ai_image5 = false, ai_image6 = false, ai_image7 = false, ai_image8 = false, ai_image9 = false, ai_image10 = false, ai_image11 = false;
    boolean bi_image1 = false, bi_image2 = false, bi_image3 = false, bi_image4 = false, bi_image5 = false, bi_image6 = false, bi_image7 = false, bi_image8 = false, bi_image9 = false, bi_image10 = false, bi_image11 = false;
    long mLastStopTime = 0;
    String categoryStep0 = "", clientNameStep0 = "";
    SearchView searchView;
    StepThreeAdapter stepThreeAdapter;
    List<CheckinStepsModel2> listNewSession;
    Button add_more;
    String actionType = "";
    SurfaceView cameraView;
    BarcodeDetector barcodeDetector;
    CameraSource cameraSource;
    final int RequestCameraPermissionID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkin);
        mChronometer = (Chronometer) findViewById(R.id.chronometerExample);
        Start();

        userSessionManager = new UserSessionManager(CheckinActivity.this);
        session = userSessionManager.getSessionDetails();
        userSessionManager.clientCategoryList = new ClientCategoryList();
        sharedHelper = new SharedHelper();
        listImages = new ArrayList<>();
        listImages = userSessionManager.bitmapList.getList();
        id = session.getId();
        gson = new Gson();
        qrcode = findViewById(R.id.barcode);

        routeid = sharedHelper.getKey(CheckinActivity.this, "routeid");
        relativeLayout0 = findViewById(R.id.relativeLayout_step0);
        relativeLayout1 = findViewById(R.id.rl1);
        relativeLayout2 = findViewById(R.id.rl2);
        relativeLayout3 = findViewById(R.id.rl3);
        relativeLayout4 = findViewById(R.id.rl4);
        relativeLayout5 = findViewById(R.id.rl5);
        relativeLayout3a = findViewById(R.id.rl3a);
        btnskip4 = findViewById(R.id.skipbtn_s4);
        searchView = findViewById(R.id.searchdoctor_SearchBox);
        add_more = findViewById(R.id.add_more);

        if (listImages.size() > 0) {
            timeWhenStopped = getIntent().getLongExtra("time", 24);
            Start();
            isBack = true;
            relativeLayout0.setVisibility(View.GONE);
            relativeLayout2.setVisibility(View.VISIBLE);
            relativeLayout1.setVisibility(View.GONE);
            getProductsAll();
        } else {
            getClientsAndCategoriesData();
        }

        murshidt = findViewById(R.id.addproduct_btn);
        linearLayout0 = findViewById(R.id.next_btn_s0);
        linearLayout1 = findViewById(R.id.next_s1btn);
        linearLayout2 = findViewById(R.id.next_s2btn);
        linearLayout3 = findViewById(R.id.next_s3btn);
        btnnext4 = findViewById(R.id.next_s4btn);
        btnnext5 = findViewById(R.id.btn_next5);
        btnpadd = findViewById(R.id.checkin_product_add);
        addproduct = findViewById(R.id.addproduct_s3);
        gridView = findViewById(R.id.simpleGridView);
        btnskip5 = findViewById(R.id.skipbtn_s5);

        clientcomments = findViewById(R.id.client_comments);
        compititorsdata = findViewById(R.id.comptitiors_data);
        ratingBar = findViewById(R.id.client_Rating);


        //add Before Images
        adbimg1 = findViewById(R.id.addbefore_product_img1);
        adbimg2 = findViewById(R.id.addbefore_product_img2);
        adbimg3 = findViewById(R.id.addbefore_product_img3);
        adbimg4 = findViewById(R.id.addbefore_product_img4);
        adbimg5 = findViewById(R.id.addbefore_product_img5);
        adbimg6 = findViewById(R.id.addbefore_product_img6);
        adbimg7 = findViewById(R.id.addbefore_product_img7);
        adbimg8 = findViewById(R.id.addbefore_product_img8);
        adbimg9 = findViewById(R.id.addbefore_product_img9);
        adbimg10 = findViewById(R.id.addbefore_product_img10);
        adbimg11 = findViewById(R.id.addbefore_product_img11);
        progressDialog = new ProgressDialog(CheckinActivity.this);

        //add After Images
        adafimg1 = findViewById(R.id.add_afterproduct_img1);
        adafimg2 = findViewById(R.id.add_afterproduct_img2);
        adafimg3 = findViewById(R.id.add_afterproduct_img3);
        adafimg4 = findViewById(R.id.add_afterproduct_img4);
        adafimg5 = findViewById(R.id.add_afterproduct_img5);
        adafimg6 = findViewById(R.id.add_afterproduct_img6);
        adafimg7 = findViewById(R.id.add_afterproduct_img7);
        adafimg8 = findViewById(R.id.add_afterproduct_img8);
        adafimg9 = findViewById(R.id.add_afterproduct_img9);
        adafimg10 = findViewById(R.id.add_afterproduct_img10);
        adafimg11 = findViewById(R.id.add_afterproduct_img11);
        context = this;
        scanImage = findViewById(R.id.search_image_btn);
        // Compititors Data images
        comp_img1 = findViewById(R.id.s5_img1);
        comp_img2 = findViewById(R.id.s5_img2);
        comp_img3 = findViewById(R.id.s5_img3);

        rgavailableonshelf = findViewById(R.id.rgb_availableshelf);
        rgnearexpiry = findViewById(R.id.rgb_nearexpiary);
        rgstockcount = findViewById(R.id.rgb_stockcount);
        rgshelfspace = findViewById(R.id.rgb_shelfspace);

        spinnerClient = findViewById(R.id.checkin_step0_client_name_spinner);
        spinnerCategory = findViewById(R.id.checkin_step0_catogories_spinner);
        clientList = new ArrayList<>();
        categoryList = new ArrayList<>();

        materialSpinner = findViewById(R.id.checkin_product_name_spinner);

        materialSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                if (item.equals("Selected Product")) {
                    SelectedText = "";
                } else {
                    SelectedText = item;
                }
            }
        });
        pcname = findViewById(R.id.checkin_client_name);
        proAvialbleshelfyes = findViewById(R.id.availableshelf_yes);
        proAvialbleshelfno = findViewById(R.id.availableshelf_no);
        pNearexpiryyes = findViewById(R.id.nearexpiry_yes);
        pNearexpiryno = findViewById(R.id.nearexpiry_no);
        pStockcountoutofstock = findViewById(R.id.rb_outofstock);
        pStockcountoverstock = findViewById(R.id.rb_overstock);
        pShelfspaceyes = findViewById(R.id.shelfspace_yes);
        pShelfspaceno = findViewById(R.id.shelfspace_no);


        listmodel1 = new ArrayList<>();
        listmodel2 = new ArrayList<>();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        gridView.setLayoutManager(linearLayoutManager);

        murshidt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                relativeLayout3.setVisibility(View.GONE);
                relativeLayout3a.setVisibility(View.VISIBLE);

            }
        });

        qrcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: qrcode");
                Intent i = new Intent(getApplicationContext(), QRscannerActivity.class);
                startActivity(i);
            }
        });

        scanImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog dialogBuilder = new AlertDialog.Builder(context).create();
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View dialogView = inflater.inflate(R.layout.qrscanner_layout, null);
                dialogBuilder.setView(dialogView);
                dialogBuilder.setCanceledOnTouchOutside(true);
                cameraView = dialogView.findViewById(R.id.cameraPreview);

                dialogBuilder.show();

                barcodeDetector = new BarcodeDetector.Builder(CheckinActivity.this).setBarcodeFormats(Barcode.CODABAR)
                        .build();

                cameraSource = new CameraSource.Builder(CheckinActivity.this, barcodeDetector).setAutoFocusEnabled(true)
                        .setRequestedPreviewSize(640, 480).build();

                cameraView.getHolder().addCallback(new SurfaceHolder.Callback() {
                    @Override
                    public void surfaceCreated(SurfaceHolder holder) {
                        //Check permissions here first


                        //If permission Granted then start Camera
                        try {
                            cameraSource.start(cameraView.getHolder());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

                    }

                    @Override
                    public void surfaceDestroyed(SurfaceHolder holder) {
                        cameraSource.stop();

                    }
                });

                barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
                    @Override
                    public void release() {

                    }

                    @Override
                    public void receiveDetections(Detector.Detections<Barcode> detections) {

                        final SparseArray<Barcode> sparseArray = detections.getDetectedItems();
                        if (sparseArray.size() != 0) {
                            dialogBuilder.dismiss();
                            Vibrator vibrator = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
                            vibrator.vibrate(1000);//here give the time in MilliSeconds
                            searchView.setQuery(sparseArray.valueAt(0).displayValue, true);
                        }
                    }
                });
            }
        });
        btnskip5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                compititor_data = "";

                PrettyDialog pDialog = new PrettyDialog(CheckinActivity.this);
                pDialog
                        .setTitle("Status")
                        .setMessage("Data Added Successfully")
                        .addButton(
                                "Finish",
                                R.color.pdlg_color_white,
                                R.color.pdlg_color_green,
                                new PrettyDialogCallback() {
                                    @Override
                                    public void onClick() {
                                        pDialog.dismiss();
                                        actionType = "finish";
                                        ShowDialouge();
                                    }
                                }
                        ).addButton(
                        "Add More",
                        R.color.pdlg_color_white,
                        R.color.pdlg_color_green,
                        new PrettyDialogCallback() {
                            @Override
                            public void onClick() {
                                pDialog.dismiss();
                                actionType = "continue";
                                ShowDialouge();
                            }
                        }
                )
                        .show();


            }
        });
        btnpadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                relativeLayout3a.setVisibility(view.GONE);
                relativeLayout3.setVisibility(view.VISIBLE);


                String productname = SelectedText;
                String customerName = pcname.getText().toString();
                String productavailable, productExpire, productStock, productSpace;

                pcname.setText("");

                if (proAvialbleshelfyes.isChecked()) {
                    productavailable = "yes";
                } else {

                    productavailable = "no";
                }


                if (pNearexpiryyes.isChecked()) {
                    productExpire = "yes";
                } else {

                    productExpire = "no";
                }


                if (pStockcountoutofstock.isChecked()) {
                    productStock = "Out of Stock";

                } else {
                    productStock = "Over Stock";
                }
                if (pShelfspaceyes.isChecked()) {
                    productSpace = "yes";
                } else {
                    productSpace = "no";
                }

                model2 = new CheckinStepsModel2(productname, customerName, productavailable, productExpire, productStock, productSpace);
                update_list(model2);
                Toast.makeText(context, "Size: " + listmodel2.size(), Toast.LENGTH_SHORT).show();
            }
        });

        addproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btnskip4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                relativeLayout4.setVisibility(View.GONE);
                relativeLayout5.setVisibility(View.GONE);
                client_comment = "";
                clientrating = ratingBar.getRating();
            }
        });

        linearLayout0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (categoryStep0.equals("") && clientNameStep0.equals("")) {
                    Toast.makeText(CheckinActivity.this, "Select Above Values", Toast.LENGTH_SHORT).show();
                } else if (categoryStep0.equals("")) {
                    Toast.makeText(CheckinActivity.this, "Select Above Values", Toast.LENGTH_SHORT).show();
                } else if (clientNameStep0.equals("")) {
                    Toast.makeText(CheckinActivity.this, "Select Above Values", Toast.LENGTH_SHORT).show();
                } else {
                    relativeLayout0.setVisibility(View.GONE);
                    relativeLayout1.setVisibility(View.VISIBLE);
                }
            }
        });

        linearLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!bi_image9 && !bi_image10 && !bi_image11) {
                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.plus);
                    userSessionManager.bitmapList.addToList(bitmap);
                    Bitmap bitmapone = BitmapFactory.decodeResource(getResources(), R.drawable.plus);
                    userSessionManager.bitmapList.addToList(bitmapone);
                    Bitmap bitmaptwo = BitmapFactory.decodeResource(getResources(), R.drawable.plus);
                    userSessionManager.bitmapList.addToList(bitmaptwo);
                    bi_image9=true;
                    bi_image10=true;
                    bi_image11=true;
                }  if (!bi_image9) {
                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.plus);
                    userSessionManager.bitmapList.addToList(bitmap);
                    bi_image9=true;
                }  if (!bi_image10) {
                    Bitmap bitmapone = BitmapFactory.decodeResource(getResources(), R.drawable.plus);
                    userSessionManager.bitmapList.addToList(bitmapone);
                    bi_image10=true;
                }  if (!bi_image11) {
                    Bitmap bitmaptwo = BitmapFactory.decodeResource(getResources(), R.drawable.plus);
                    userSessionManager.bitmapList.addToList(bitmaptwo);
                    bi_image11=true;
                }
                listImages = new ArrayList<>();
                listImages = userSessionManager.bitmapList.getList();
                if (listImages.size() == 11) {
                    Stop();
                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                    intent.putExtra("time", timeWhenStopped);
                    startActivity(intent);
                } else {
                    Toast.makeText(context, "Select Minimum First 8 Images", Toast.LENGTH_SHORT).show();
                }
               /* relativeLayout1.setVisibility(view.GONE);
                relativeLayout2.setVisibility(view.VISIBLE);*/
            }
        });
        linearLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ai_image1 && ai_image2 && ai_image3 && ai_image4 && ai_image5 && ai_image6 && ai_image7 && ai_image8) {

                        relativeLayout2.setVisibility(view.GONE);
                        relativeLayout3.setVisibility(view.VISIBLE);


                } else {
                    Toast.makeText(context, "Select All Images", Toast.LENGTH_SHORT).show();
                }
            }
        });
        linearLayout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listNewSession = new ArrayList<>();
                userSessionManager.stepThreeList = new StepThreeList();
                listNewSession = userSessionManager.stepThreeList.getList();
                if (listNewSession.size() > 0) {
                    relativeLayout3.setVisibility(view.GONE);
                    relativeLayout4.setVisibility(view.VISIBLE);
                } else {
                    Toast.makeText(context, "Select One Item", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnnext4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (clientcomments.getText().toString().isEmpty()) {
                    Toast.makeText(context, "Please Type Comment", Toast.LENGTH_SHORT).show();
                } else {
                    relativeLayout4.setVisibility(view.GONE);
                    relativeLayout5.setVisibility(view.VISIBLE);
                    client_comment = clientcomments.getText().toString();
                    clientrating = ratingBar.getRating();
                }


            }
        });

        btnnext5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (compititorsdata.getText().toString().isEmpty()) {
                    Toast.makeText(context, "Enter Data", Toast.LENGTH_SHORT).show();
                } else {
                    compititor_data = compititorsdata.getText().toString();
                    if (cc_image1 && cc_image2 && cc_image3)
                        ShowDialougesubmit();
                    else
                        Toast.makeText(context, "Select Required Data", Toast.LENGTH_SHORT).show();
                }

            }
        });


        adbimg1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetImageFromGallery(1000);

            }
        });


        adbimg2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetImageFromGallery(2000);

            }
        });


        adbimg3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetImageFromGallery(3000);

            }
        });


        adbimg4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetImageFromGallery(4000);

            }
        });


        adbimg5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetImageFromGallery(5000);

            }
        });


        adbimg6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetImageFromGallery(6000);

            }
        });

        addproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });


        adbimg7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetImageFromGallery(7000);

            }
        });


        adafimg1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetImageFromGallery(8000);

            }
        });


        adafimg2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetImageFromGallery(9000);

            }
        });


        adafimg3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetImageFromGallery(10000);

            }
        });


        adafimg4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetImageFromGallery(11000);

            }
        });


        adafimg5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetImageFromGallery(12000);

            }
        });


        adafimg6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetImageFromGallery(13000);

            }
        });


        adafimg7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetImageFromGallery(14000);

            }
        });


        comp_img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetImageFromGallery(15000);

            }
        });

        comp_img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetImageFromGallery(16000);

            }
        });

        comp_img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetImageFromGallery(17000);

            }
        });
        adbimg8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetImageFromGallery(18000);

            }
        });
        adbimg9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetImageFromGallery(19000);

            }
        });
        adbimg10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetImageFromGallery(20000);

            }
        });
        adbimg11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetImageFromGallery(21000);

            }
        });
        adafimg8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetImageFromGallery(22000);

            }
        });
        adafimg9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetImageFromGallery(23000);

            }
        });
        adafimg10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetImageFromGallery(24000);

            }
        });
        adafimg11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetImageFromGallery(25000);

            }
        });
        add_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionType = "continue";
                ShowDialouge();
            }
        });

    }

    private void ShowDialougesubmit() {
        final android.app.AlertDialog dialogBuilder = new android.app.AlertDialog.Builder(CheckinActivity.this).create();
        // LayoutInflater inflater = context.getApplicationContext().getLayoutInflater();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View dialogView = inflater.inflate(R.layout.stepfive_popup, null);

        dialogBuilder.setView(dialogView);
        dialogBuilder.setCanceledOnTouchOutside(true);
        dialogBuilder.show();
        CardView CheckoutTask = dialogView.findViewById(R.id.step5_checkout);
        CardView ContinueTask = dialogView.findViewById(R.id.step5_continueTask);

        CheckoutTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogBuilder.dismiss();
                popText = "Are You Sure You Want To CheckOut? Yes Or No";
                actionType = "finish";
                ShowDialouge();
            }
        });
        ContinueTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogBuilder.dismiss();
                actionType = "continue";
                popText = "Are You Sure You Want To Continue? Yes Or No";
                ShowDialouge();
            }
        });

    }

    private void getClientsAndCategoriesData() {
        final ProgressDialog progressDialog1 = new ProgressDialog(CheckinActivity.this);
        progressDialog1.setMessage("Please Wait");
        progressDialog1.setCanceledOnTouchOutside(false);
        progressDialog1.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://demonline.tech/app_admin/api/get_client_categories.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog1.dismiss();
                try {
                    JSONObject clients, categories;
                    JSONObject jsonObject = new JSONObject(response);
                    boolean status = jsonObject.getBoolean("status");
                    if (status) {
                        JSONArray jsonArray = jsonObject.getJSONArray("clients");
                        JSONArray jsonArray1 = jsonObject.getJSONArray("categories");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            clients = jsonArray.getJSONObject(i);
                            int id = clients.getInt("id");
                            String name = clients.getString("name");
                            ClientNameStep model = new ClientNameStep(id, name);
                            clientList.add(model);
                        }
                        for (int i = 0; i < jsonArray1.length(); i++) {
                            categories = jsonArray1.getJSONObject(i);
                            int id = categories.getInt("id");
                            String name = categories.getString("name");
                            CategoryNameStep model1 = new CategoryNameStep(id, name);
                            categoryList.add(model1);
                        }
                        setClientSpinner();
                        setCategoriesSpinner();
                    }
                } catch (JSONException e) {
                    Toast.makeText(CheckinActivity.this, "JSON EXCEPTION: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog1.dismiss();
                Toast.makeText(CheckinActivity.this, "Volley: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                getClientsAndCategoriesData();
            }
        });

        Volley.newRequestQueue(CheckinActivity.this).add(stringRequest);
    }

    void setCategoriesSpinner() {

        SpinnerAdapter spinnerAdater;
        List<String> cities = getCategoriesForSpinner();

        spinnerAdater = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, cities);
        spinnerCategory.setAdapter(spinnerAdater);

        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String tvSelectedCity = parent.getItemAtPosition(position).toString();
                if (tvSelectedCity.equals("Select Category")) {
                    selectedCategory = null;
                    //  areaVillageSpinnerLayout.setVisibility(View.GONE);
                    return;
                }

                for (CategoryNameStep item : categoryList) {
                    if (item.getName().equals(tvSelectedCity)) {
                        selectedCategory = item;
                    }
                }
                categoryStep0 = selectedCategory.getName();
                userSessionManager.clientCategoryList.setList(categoryStep0, "cat");

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private List<String> getCategoriesForSpinner() {
        List<String> cities = new ArrayList<>();
        cities.add("Select Category");
        for (CategoryNameStep item : categoryList) {
            cities.add(item.getName());
        }
        return cities;
    }


    void setClientSpinner() {

        SpinnerAdapter spinnerAdater;
        List<String> cities = getClientsForSpinner();

        spinnerAdater = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, cities);
        spinnerClient.setAdapter(spinnerAdater);

        spinnerClient.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String tvSelectedCity = parent.getItemAtPosition(position).toString();
                if (tvSelectedCity.equals("Select Client")) {
                    selectedClient = null;
                    //  areaVillageSpinnerLayout.setVisibility(View.GONE);
                    return;
                }

                for (ClientNameStep item : clientList) {
                    if (item.getName().equals(tvSelectedCity)) {
                        selectedClient = item;
                    }
                }
                clientNameStep0 = selectedClient.getName();
                userSessionManager.clientCategoryList.setList(clientNameStep0, "cli");

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private List<String> getClientsForSpinner() {
        List<String> cities = new ArrayList<>();
        cities.add("Select Client");
        for (ClientNameStep item : clientList) {
            cities.add(item.getName());
        }
        return cities;
    }


    private void ShowDialouge() {
        Rect displayRectangle = new Rect();
        Window window = CheckinActivity.this.getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);
        final AlertDialog dialogBuilder = new AlertDialog.Builder(CheckinActivity.this, R.style.CustomAlertDialog2).create();
        // LayoutInflater inflater = context.getApplicationContext().getLayoutInflater();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View dialogView = inflater.inflate(R.layout.checkout_dialouge_layout, null);
        dialogView.setMinimumWidth((int) (displayRectangle.width() * 1f));
        dialogView.setMinimumHeight((int) (displayRectangle.height() * 1f));
        dialogBuilder.setView(dialogView);
        dialogBuilder.setCanceledOnTouchOutside(true);
        TextView textView = dialogView.findViewById(R.id.checkout_DialougeText);
        Button yes = dialogView.findViewById(R.id.checkoutDialouge_yes);
        Button no = dialogView.findViewById(R.id.checkoutDialouge_no);
        TextView timeText = dialogView.findViewById(R.id.cdl_time);
        textView.setText(popText);
        dialogBuilder.show();
        timeText.setText(mChronometer.getText());
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogBuilder.dismiss();
            }
        });
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gettodaytask("https://demonline.tech/app_admin/api/today_tasks.php");
            }
        });
    }

    private void getProductsAll() {
        listProducts = new ArrayList<>();
        List<String> listcatcli = new ArrayList<>();

        listcatcli = userSessionManager.clientCategoryList.getList();
        String Valuee = listcatcli.get(1);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://demonline.tech/app_admin/api/get_category_items.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean status = jsonObject.getBoolean("status");
                    if (status) {
                        JSONObject object;
                        JSONArray jsonArray = jsonObject.getJSONArray("items");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            object = jsonArray.getJSONObject(i);
                            listProducts.add(new StepThreeModel(object.getString("name"), object.getString("shelf"), object.getString("barcode"), object.getString("id")));
                        }
                        stepThreeAdapter = new StepThreeAdapter(listProducts, CheckinActivity.this);

                        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                            @Override
                            public boolean onQueryTextSubmit(String s) {
                                return false;
                            }

                            @Override
                            public boolean onQueryTextChange(String s) {
                                stepThreeAdapter.getFilter().filter(s);
                                return false;
                            }
                        });

                        gridView.setAdapter(stepThreeAdapter);
                      //  Toast.makeText(context, "List P: " + String.valueOf(listProducts.size()), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(context, "JSON Exception: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(context, "Volley Error", Toast.LENGTH_SHORT).show();
                getProductsAll();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("cat_name", Valuee);
                return map;
            }
        };
        Volley.newRequestQueue(CheckinActivity.this).add(stringRequest);
    }

    private void SaveDataToDataBase() {

        list2 = gson.toJson(listNewSession);
        if (!ai_image9 && !ai_image10 && !ai_image11) {
            Bitmap bitmaptwo = BitmapFactory.decodeResource(getResources(), R.drawable.plus);
            adafimg9.setImageBitmap(bitmaptwo);
            adafimg10.setImageBitmap(bitmaptwo);
            adafimg11.setImageBitmap(bitmaptwo);
            ai_image9=true;
            ai_image10=true;
            ai_image11=true;
        }
        if (!ai_image9) {
            Bitmap bitmaptwo = BitmapFactory.decodeResource(getResources(), R.drawable.plus);
            adafimg9.setImageBitmap(bitmaptwo);
        }
        if (!ai_image10) {
            Bitmap bitmaptwo = BitmapFactory.decodeResource(getResources(), R.drawable.plus);
            adafimg10.setImageBitmap(bitmaptwo);
        }
        if (!ai_image11) {
            Bitmap bitmaptwo = BitmapFactory.decodeResource(getResources(), R.drawable.plus);
            adafimg11.setImageBitmap(bitmaptwo);
        }

        SaveDataToDataBaseNext(list2);
    }

    private void SaveDataToDataBaseNext(String list2) {


        progressDialog.setTitle("Adding Data");
        progressDialog.setMessage("Please Wait");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        VolleyMultipartRequest request = new VolleyMultipartRequest(Request.Method.POST, "https://demonline.tech/app_admin/api/CheckList.php", new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {

                progressDialog.dismiss();
                String res = new String(response.data);
                try {
                    JSONObject jsonObject = new JSONObject(res);
                    boolean status = jsonObject.getBoolean("status");
                    if (status) {

                        PrettyDialog pDialog = new PrettyDialog(CheckinActivity.this);
                        pDialog.setIcon(R.drawable.ic_check_circle_black_24dp)
                                .setTitle("Status")
                                .setMessage("Data Added Successfully")
                                .addButton(
                                        "Done",
                                        R.color.pdlg_color_white,
                                        R.color.pdlg_color_green,
                                        new PrettyDialogCallback() {
                                            @Override
                                            public void onClick() {
                                                pDialog.dismiss();
                                                userSessionManager.bitmapList.list.clear();
                                                userSessionManager.stepThreeList.list.clear();
                                                userSessionManager.clientCategoryList.list.clear();
                                                if (actionType.equals("continue")) {
                                                    Intent intent = new Intent(getApplicationContext(), CheckinActivity.class);
                                                    startActivity(intent);
                                                    finish();
                                                } else {
                                                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                                                    startActivity(intent);
                                                    finish();
                                                }

                                            }
                                        }
                                )
                                .show();

                    } else {
                        Toast.makeText(context, "Not Submitted", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(context, "Exception: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                String json = null;
                String Message;
                NetworkResponse response = error.networkResponse;
                if (response != null && response.data != null) {
                    try {
                        JSONObject errorObj = new JSONObject(new String(response.data));
                        if (response.getClass().equals(TimeoutError.class)) {
                            SaveDataToDataBaseNext(list2);
                            return;
                        }
                        if (response.statusCode == 400 || response.statusCode == 405 || response.statusCode == 500) {
                            try {
                                displayMessage(errorObj.optString("message"));
                            } catch (Exception e) {
                                displayMessage("Some Thing went wrong");
                            }
                        } else if (response.statusCode == 401) {

                        } else if (response.statusCode == 422) {

                            json = GeneralData.trimMessage(new String(response.data));
                            if (json != "" && json != null) {
                                displayMessage(json);
                            } else {
                                displayMessage("Please try again");
                            }

                        } else if (response.statusCode == 503) {
                            displayMessage("Server Down");
                        } else {
                            displayMessage("Please try again");
                        }

                    } catch (Exception e) {
                        displayMessage("Some thing went wrong");
                    }

                } else {
                    if (error instanceof NoConnectionError) {
                        displayMessage("Connect Internet");
                    } else if (error instanceof NetworkError) {
                        displayMessage("Connect Internet");
                    } else if (error instanceof TimeoutError) {
                        SaveDataToDataBaseNext(list2);
                    }
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("list2", list2);
                map.put("time", mChronometer.getText().toString());
                map.put("user_id", String.valueOf(id));
                map.put("route_id", routeid);
                map.put("action_type", actionType);
                map.put("client_comment", client_comment);
                map.put("comptitor_data", compititor_data);
                map.put("client_rating", String.valueOf(clientrating));
                return map;
            }

            @Override
            protected Map<String, DataPart> getByteData() throws AuthFailureError {
                Map<String, VolleyMultipartRequest.DataPart> dataPartMap = new HashMap<>();
                adbimg1.setImageBitmap(listImages.get(0));
                adbimg2.setImageBitmap(listImages.get(1));
                adbimg3.setImageBitmap(listImages.get(2));
                adbimg4.setImageBitmap(listImages.get(3));
                adbimg5.setImageBitmap(listImages.get(4));
                adbimg6.setImageBitmap(listImages.get(5));
                adbimg7.setImageBitmap(listImages.get(6));
                adbimg8.setImageBitmap(listImages.get(7));
                adbimg9.setImageBitmap(listImages.get(8));
                adbimg10.setImageBitmap(listImages.get(9));
                adbimg11.setImageBitmap(listImages.get(10));

                dataPartMap.put("bi1", new VolleyMultipartRequest.DataPart("bi1.jpg", AppHelper.getFileDataFromDrawable(adbimg1.getDrawable()), "image/jpeg"));
                dataPartMap.put("bi2", new VolleyMultipartRequest.DataPart("bi2.jpg", AppHelper.getFileDataFromDrawable(adbimg2.getDrawable()), "image/jpeg"));
                dataPartMap.put("bi3", new VolleyMultipartRequest.DataPart("bi3.jpg", AppHelper.getFileDataFromDrawable(adbimg3.getDrawable()), "image/jpeg"));
                dataPartMap.put("bi4", new VolleyMultipartRequest.DataPart("bi4.jpg", AppHelper.getFileDataFromDrawable(adbimg4.getDrawable()), "image/jpeg"));
                dataPartMap.put("bi5", new VolleyMultipartRequest.DataPart("bi5.jpg", AppHelper.getFileDataFromDrawable(adbimg5.getDrawable()), "image/jpeg"));
                dataPartMap.put("bi6", new VolleyMultipartRequest.DataPart("bi6.jpg", AppHelper.getFileDataFromDrawable(adbimg6.getDrawable()), "image/jpeg"));
                dataPartMap.put("bi7", new VolleyMultipartRequest.DataPart("bi7.jpg", AppHelper.getFileDataFromDrawable(adbimg7.getDrawable()), "image/jpeg"));

                dataPartMap.put("bi8", new VolleyMultipartRequest.DataPart("bi8.jpg", AppHelper.getFileDataFromDrawable(adbimg8.getDrawable()), "image/jpeg"));
                dataPartMap.put("bi9", new VolleyMultipartRequest.DataPart("bi9.jpg", AppHelper.getFileDataFromDrawable(adbimg9.getDrawable()), "image/jpeg"));
                dataPartMap.put("bi10", new VolleyMultipartRequest.DataPart("bi10.jpg", AppHelper.getFileDataFromDrawable(adbimg10.getDrawable()), "image/jpeg"));
                dataPartMap.put("bi11", new VolleyMultipartRequest.DataPart("bi11.jpg", AppHelper.getFileDataFromDrawable(adbimg11.getDrawable()), "image/jpeg"));

                dataPartMap.put("ai1", new VolleyMultipartRequest.DataPart("ai1.jpg", AppHelper.getFileDataFromDrawable(adafimg1.getDrawable()), "image/jpeg"));
                dataPartMap.put("ai2", new VolleyMultipartRequest.DataPart("ai2.jpg", AppHelper.getFileDataFromDrawable(adafimg2.getDrawable()), "image/jpeg"));
                dataPartMap.put("ai3", new VolleyMultipartRequest.DataPart("ai3.jpg", AppHelper.getFileDataFromDrawable(adafimg3.getDrawable()), "image/jpeg"));
                dataPartMap.put("ai4", new VolleyMultipartRequest.DataPart("ai4.jpg", AppHelper.getFileDataFromDrawable(adafimg4.getDrawable()), "image/jpeg"));
                dataPartMap.put("ai5", new VolleyMultipartRequest.DataPart("ai5.jpg", AppHelper.getFileDataFromDrawable(adafimg5.getDrawable()), "image/jpeg"));
                dataPartMap.put("ai6", new VolleyMultipartRequest.DataPart("ai6.jpg", AppHelper.getFileDataFromDrawable(adafimg6.getDrawable()), "image/jpeg"));
                dataPartMap.put("ai7", new VolleyMultipartRequest.DataPart("ai7.jpg", AppHelper.getFileDataFromDrawable(adafimg7.getDrawable()), "image/jpeg"));

                dataPartMap.put("ai8", new VolleyMultipartRequest.DataPart("ai8.jpg", AppHelper.getFileDataFromDrawable(adafimg8.getDrawable()), "image/jpeg"));
                dataPartMap.put("ai9", new VolleyMultipartRequest.DataPart("ai9.jpg", AppHelper.getFileDataFromDrawable(adafimg9.getDrawable()), "image/jpeg"));
                dataPartMap.put("ai10", new VolleyMultipartRequest.DataPart("ai10.jpg", AppHelper.getFileDataFromDrawable(adafimg10.getDrawable()), "image/jpeg"));
                dataPartMap.put("ai11", new VolleyMultipartRequest.DataPart("ai11.jpg", AppHelper.getFileDataFromDrawable(adafimg11.getDrawable()), "image/jpeg"));


                dataPartMap.put("ci1", new VolleyMultipartRequest.DataPart("ci1.jpg", AppHelper.getFileDataFromDrawable(comp_img1.getDrawable()), "image/jpeg"));
                dataPartMap.put("ci2", new VolleyMultipartRequest.DataPart("ci2.jpg", AppHelper.getFileDataFromDrawable(comp_img2.getDrawable()), "image/jpeg"));
                dataPartMap.put("ci3", new VolleyMultipartRequest.DataPart("ci3.jpg", AppHelper.getFileDataFromDrawable(comp_img3.getDrawable()), "image/jpeg"));
                return dataPartMap;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(
                250000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(context).add(request);
    }


    private void update_list(CheckinStepsModel2 model2) {
        listmodel2.add(model2);
   /*     customAdapter = new CustomAdapterRecycler(context, listmodel2);
        gridView.setAdapter(customAdapter);
        customAdapter.notifyDataSetChanged();*/

    }

    public void displayMessage(String toastString) {
        try {
            Snackbar.make(getCurrentFocus(), toastString, Snackbar.LENGTH_SHORT)
                    .setAction("Action", null).show();
        } catch (Exception e) {
            try {
                Toast.makeText(context, "" + toastString, Toast.LENGTH_SHORT).show();
            } catch (Exception ee) {
                ee.printStackTrace();
            }
        }
    }

    public void Stop() {
        timeWhenStopped = mChronometer.getBase() - SystemClock.elapsedRealtime();
        mChronometer.stop();
    }

    public void Start() {
        mChronometer.setBase(SystemClock.elapsedRealtime() + timeWhenStopped);
        mChronometer.start();
    }

    private void GetImageFromGallery(int SELECT_PHOTO) {

      /*  Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PHOTO);
*/
        File outputFile = new File(Environment.getExternalStorageDirectory().toString());

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFile);
        startActivityForResult(intent, SELECT_PHOTO);
    }

    // 1):
    @Override
    protected void onPause() {
        super.onPause();
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.cancel();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.cancel();
        }
    }

    // 2) :
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.cancel();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {


        if (requestCode == 1000 && resultCode == RESULT_OK && data.getExtras().get("data") != null) {
            uri = data.getData();

            bitmap = (Bitmap) data.getExtras().get("data");
            userSessionManager.bitmapList.addToList(bitmap);
            bi_image1 = true;
            adbimg1.setImageBitmap(bitmap);
            /*    add_bimg1 = ImagerToString(bitmap);*/


        } else if (requestCode == 2000 && resultCode == RESULT_OK && data.getExtras().get("data") != null) {
            uri = data.getData();

            bitmap1 = (Bitmap) data.getExtras().get("data");
            userSessionManager.bitmapList.addToList(bitmap1);
            bi_image2 = true;
            adbimg2.setImageBitmap(bitmap1);


        } else if (requestCode == 3000 && resultCode == RESULT_OK && data.getExtras().get("data") != null) {
            uri = data.getData();

            bitmap2 = (Bitmap) data.getExtras().get("data");
            userSessionManager.bitmapList.addToList(bitmap2);
            bi_image3 = true;
            adbimg3.setImageBitmap(bitmap2);


        } else if (requestCode == 4000 && resultCode == RESULT_OK && data.getExtras().get("data") != null) {
            uri = data.getData();

            bitmap3 = (Bitmap) data.getExtras().get("data");
            userSessionManager.bitmapList.addToList(bitmap3);
            bi_image4 = true;
            adbimg4.setImageBitmap(bitmap3);


        } else if (requestCode == 5000 && resultCode == RESULT_OK && data.getExtras().get("data") != null) {
            uri = data.getData();

            bitmap4 = (Bitmap) data.getExtras().get("data");
            userSessionManager.bitmapList.addToList(bitmap4);
            bi_image5 = true;
            adbimg5.setImageBitmap(bitmap4);


        } else if (requestCode == 6000 && resultCode == RESULT_OK && data.getExtras().get("data") != null) {
            uri = data.getData();

            bitmap5 = (Bitmap) data.getExtras().get("data");
            userSessionManager.bitmapList.addToList(bitmap5);
            bi_image6 = true;
            adbimg6.setImageBitmap(bitmap5);


        } else if (requestCode == 7000 && resultCode == RESULT_OK && data.getExtras().get("data") != null) {
            uri = data.getData();

            bitmap6 = (Bitmap) data.getExtras().get("data");
            userSessionManager.bitmapList.addToList(bitmap6);
            bi_image7 = true;
            adbimg7.setImageBitmap(bitmap6);


        } else if (requestCode == 8000 && resultCode == RESULT_OK && data.getExtras().get("data") != null) {
            uri = data.getData();

            bitmap7 = (Bitmap) data.getExtras().get("data");
            adafimg1.setImageBitmap(bitmap7);
            ai_image1 = true;


        } else if (requestCode == 9000 && resultCode == RESULT_OK && data.getExtras().get("data") != null) {
            uri = data.getData();

            bitmap8 = (Bitmap) data.getExtras().get("data");
            adafimg2.setImageBitmap(bitmap8);
            ai_image2 = true;

        } else if (requestCode == 10000 && resultCode == RESULT_OK && data.getExtras().get("data") != null) {
            uri = data.getData();

            bitmap9 = (Bitmap) data.getExtras().get("data");
            adafimg3.setImageBitmap(bitmap9);
            ai_image3 = true;

        } else if (requestCode == 11000 && resultCode == RESULT_OK && data.getExtras().get("data") != null) {
            uri = data.getData();

            bitmap10 = (Bitmap) data.getExtras().get("data");
            adafimg4.setImageBitmap(bitmap10);
            ai_image4 = true;


        } else if (requestCode == 12000 && resultCode == RESULT_OK && data.getExtras().get("data") != null) {
            uri = data.getData();

            bitmap11 = (Bitmap) data.getExtras().get("data");
            adafimg5.setImageBitmap(bitmap11);
            ai_image5 = true;

        } else if (requestCode == 13000 && resultCode == RESULT_OK && data.getExtras().get("data") != null) {
            uri = data.getData();

            bitmap12 = (Bitmap) data.getExtras().get("data");
            adafimg6.setImageBitmap(bitmap12);
            ai_image6 = true;


        } else if (requestCode == 14000 && resultCode == RESULT_OK && data.getExtras().get("data") != null) {
            uri = data.getData();

            bitmap13 = (Bitmap) data.getExtras().get("data");
            adafimg7.setImageBitmap(bitmap13);
            ai_image7 = true;


        } else if (requestCode == 15000 && resultCode == RESULT_OK && data.getExtras().get("data") != null) {
            uri = data.getData();

            bitmap14 = (Bitmap) data.getExtras().get("data");
            comp_img1.setImageBitmap(bitmap14);
            cc_image1 = true;


        } else if (requestCode == 16000 && resultCode == RESULT_OK && data.getExtras().get("data") != null) {
            uri = data.getData();

            bitmap15 = (Bitmap) data.getExtras().get("data");
            comp_img2.setImageBitmap(bitmap15);
            cc_image2 = true;

        } else if (requestCode == 17000 && resultCode == RESULT_OK && data.getExtras().get("data") != null) {
            uri = data.getData();

            bitmap16 = (Bitmap) data.getExtras().get("data");
            comp_img3.setImageBitmap(bitmap16);

            cc_image3 = true;
        } else if (requestCode == 18000 && resultCode == RESULT_OK && data.getExtras().get("data") != null) {
            uri = data.getData();

            bitmap18 = (Bitmap) data.getExtras().get("data");
            userSessionManager.bitmapList.addToList(bitmap18);
            bi_image8 = true;
            adbimg8.setImageBitmap(bitmap18);


        } else if (requestCode == 19000 && resultCode == RESULT_OK && data.getExtras().get("data") != null) {
            uri = data.getData();

            bitmap19 = (Bitmap) data.getExtras().get("data");
            userSessionManager.bitmapList.addToList(bitmap19);
            bi_image9 = true;
            adbimg9.setImageBitmap(bitmap19);


        } else if (requestCode == 20000 && resultCode == RESULT_OK && data.getExtras().get("data") != null) {
            uri = data.getData();

            bitmap20 = (Bitmap) data.getExtras().get("data");
            userSessionManager.bitmapList.addToList(bitmap19);
            bi_image10 = true;
            adbimg10.setImageBitmap(bitmap20);

        } else if (requestCode == 21000 && resultCode == RESULT_OK && data.getExtras().get("data") != null) {
            uri = data.getData();

            bitmap21 = (Bitmap) data.getExtras().get("data");
            userSessionManager.bitmapList.addToList(bitmap21);
            bi_image11 = true;
            adbimg11.setImageBitmap(bitmap21);


        } else if (requestCode == 22000 && resultCode == RESULT_OK && data.getExtras().get("data") != null) {
            uri = data.getData();

            bitmap22 = (Bitmap) data.getExtras().get("data");
            adafimg8.setImageBitmap(bitmap22);
            ai_image8 = true;
        } else if (requestCode == 23000 && resultCode == RESULT_OK && data.getExtras().get("data") != null) {
            uri = data.getData();

            bitmap23 = (Bitmap) data.getExtras().get("data");
            adafimg9.setImageBitmap(bitmap23);
            ai_image9 = true;
        } else if (requestCode == 24000 && resultCode == RESULT_OK && data.getExtras().get("data") != null) {
            uri = data.getData();

            bitmap24 = (Bitmap) data.getExtras().get("data");
            adafimg10.setImageBitmap(bitmap24);
            ai_image10 = true;
        } else if (requestCode == 25000 && resultCode == RESULT_OK && data.getExtras().get("data") != null) {
            uri = data.getData();

            bitmap25 = (Bitmap) data.getExtras().get("data");
            adafimg11.setImageBitmap(bitmap25);
            ai_image11 = true;
        }
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void onBackPressed() {
        if (isBack) {
            Toast.makeText(context, "Please Complete the Task First.", Toast.LENGTH_SHORT).show();
        } else {
            super.onBackPressed();
        }
    }

    public void gettodaytask(String url){

        pd = new ProgressDialog(this);
        pd.setMessage("Signing in....");

        pd.setMessage(Html.fromHtml("<font color='#000000'>Wait for a moment</font>"));
        pd.getWindow().setBackgroundDrawableResource(R.color.colorWhite);

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("taskresponse",response);
                try  {
                    JSONObject object = new JSONObject(response);

                    JSONArray jsonArray=object.getJSONArray("data");

                    if(jsonArray.length()>0)
                    {
                        Toast.makeText(context, "You have "+jsonArray.length()+" task(s) pending", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(context, "Tasks done , checking out", Toast.LENGTH_SHORT).show();
                    }

                    SaveDataToDataBase();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                pd.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pd.dismiss();
                Log.d("loginError : ", error.getMessage()+"");
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("user_id", String.valueOf(session.getId()));
                return map;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(this);
        pd.show();
        queue.add(request);


    }

}