package com.el.mkoba;

import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ybq.android.spinkit.style.ChasingDots;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;
import id.zelory.compressor.Compressor;

import static com.theartofdev.edmodo.cropper.CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE;

public class RegActivity extends AppCompatActivity {


    TextView toLog;

    private EditText firstName,lastName,id_NO,phoneNumber,location,Email,Password;
    private TextView toReg,Add_image;
    private Button btnClientSubmit;
    //Dialog
    private AlertDialog dialog2,dialog3;

    private ProgressBar progressDialog;

    private FirebaseAuth mAuth;


    private String FirstName,LastName,PhoneNumber,ID_no,Location,email ;

    ///Database 0---
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference MkobaRef = db.collection("Mkoba_Account");

    private CircleImageView Client_image;

    private Uri ImageUri;
    FirebaseStorage storage;
    StorageReference storageReference;
    private ProgressBar progressBar;
    private Bitmap compressedImageBitmap;

    private UploadTask uploadTask;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);


        mAuth = FirebaseAuth.getInstance();

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        //EditText
        firstName = findViewById(R.id.Client_FirstName);
        lastName = findViewById(R.id.Client_LastName);
        phoneNumber = findViewById(R.id.Client_Phone_number);
        id_NO = findViewById(R.id.Client_Id_No);
        location = findViewById(R.id.Client_Location);
        Email = findViewById(R.id.Client_Email);
        Password = findViewById(R.id.Client_Password);

        //Button
        btnClientSubmit = findViewById(R.id.Client_Submit);

        progressDialog = findViewById(R.id.Progress12);

        ChasingDots cashingDots = new ChasingDots();
        progressDialog.setIndeterminateDrawable(cashingDots);



        btnClientSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!ValidaTion()){


                }else {

                    RegisterClient();
                }



            }
        });

        toLog = findViewById(R.id.Client_log);

        toLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent toreg = new Intent(getApplicationContext(),LogInActivity.class);
                startActivity(toreg);
            }
        });
    }



    private void ImageUpload_dialog(){

        final  AlertDialog.Builder mbuilder = new AlertDialog.Builder(this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_add_image, null);
        mbuilder.setView(mView);
        Client_image = mView.findViewById(R.id.My_image);
        Add_image = mView.findViewById(R.id.Image_add);
        TextView closeDia = mView.findViewById(R.id.CloseImage);

        closeDia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog2.dismiss();
            }
        });

        Client_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CropImage.activity().setGuidelines(CropImageView.Guidelines.ON)
                        .setMinCropResultSize(512,512)
                        .setAspectRatio(1,1)
                        .start(RegActivity.this);
            }
        });
        Add_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CropImage.activity().setGuidelines(CropImageView.Guidelines.ON)
                        .setMinCropResultSize(512,512)
                        .setAspectRatio(1,1)
                        .start(RegActivity.this);

            }
        });



        dialog2 = mbuilder.create();
        dialog2.show();
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK)
            switch (requestCode) {
                case CROP_IMAGE_ACTIVITY_REQUEST_CODE:
                    CropImage.ActivityResult result = CropImage.getActivityResult(data);

                    //data.getData returns the content URI for the selected Image

                    ImageUri = result.getUri();
                    Client_image.setImageURI(ImageUri);

                    dialog2.dismiss();
                    break;


            }


    }



    private void RegisterClient() {

        if (ImageUri == null){

            Toast.makeText(this, "Upload profile photo", Toast.LENGTH_SHORT).show();
            ImageUpload_dialog();

        }else {

                        email =Email.getText().toString();
                        String password =Password.getText().toString();

                        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if(task.isSuccessful()){

                                    Store_Image_and_details();

                                }else {


                                }

                            }
                        });

        }


    }

    private void Store_Image_and_details() {



        progressDialog.setVisibility(View.VISIBLE);
        btnClientSubmit.setEnabled(false);
        btnClientSubmit.setVisibility(View.INVISIBLE);


        try {
            File newimage = new File(ImageUri.getPath());
            Compressor compressor = new Compressor(this);
            compressor.setMaxHeight(200);
            compressor.setMaxWidth(200);
            compressor.setQuality(10);
            compressedImageBitmap = compressor.compressToBitmap(newimage);
        } catch (IOException e) {
            e.printStackTrace();
        }


        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        compressedImageBitmap.compress(Bitmap.CompressFormat.JPEG,100,baos);
        final byte[] data = baos.toByteArray();


        final StorageReference ref = storageReference.child("Users/thumbs" + UUID.randomUUID().toString());
        uploadTask = ref.putBytes(data);

        uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }
                // Continue with the task to get the download URL
                return ref.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {

                Uri downloadUri = task.getResult();
                String profileImage = downloadUri.toString();


                Random dice = new Random();
                int AccountNO =dice.nextInt(100000000)+3;
                final String Mkoba_Id =String.valueOf(AccountNO);

                FirstName =firstName.getText().toString();
                LastName =lastName.getText().toString();
                PhoneNumber = phoneNumber.getText().toString();
                ID_no = id_NO.getText().toString();
                Location = location.getText().toString();
                email =Email.getText().toString();
                String password =Password.getText().toString();

                final double balance = Double.valueOf("0.00");
                final double expences = Double.valueOf("0.00");

                Toast.makeText(RegActivity.this, "Sign Up successful.", Toast.LENGTH_SHORT).show();

                Intent toreg = new Intent(getApplicationContext(),PayMethodActivity.class);
                startActivity(toreg);
                finish();

                String refreshtoken = FirebaseInstanceId.getInstance().getToken();

                String User_ID = mAuth.getCurrentUser().getUid();

                HashMap<String,Object> store = new HashMap<>();
                store.put("First_Name",FirstName);
                store.put("Last_Name",LastName);
                store.put("Location",Location);
                store.put("Phone",PhoneNumber);
                store.put("Email",email);
                store.put("Id_No",ID_no);
                store.put("User_ID",User_ID);
                store.put("device_token",refreshtoken);
                store.put("balance",0.00);
                store.put("Mkoba_ID",Mkoba_Id);
                store.put("expences",0.00);
                store.put("Profile_Image",profileImage);


                MkobaRef.document(User_ID).set(store).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@androidx.annotation.NonNull Task<Void> task) {

                        if (task.isSuccessful()){

                            btnClientSubmit.setEnabled(true);
                            progressDialog.setVisibility(View.INVISIBLE);

                            Toast.makeText(RegActivity.this, "Registration successful.", Toast.LENGTH_SHORT).show();
                            Intent toreg = new Intent(getApplicationContext(),PayMethodActivity.class);
                            startActivity(toreg);
                            finish();

                        }else {

                            Toast.makeText(RegActivity.this, "Registration failed try Again.", Toast.LENGTH_SHORT).show();

                            btnClientSubmit.setEnabled(true);
                            progressDialog.setVisibility(View.INVISIBLE);
                        }
                    }
                });









            }});



    }


    private boolean ValidaTion() {

        String FirstName =firstName.getText().toString();
        String LastName =lastName.getText().toString();
        String PhoneNumber = phoneNumber.getText().toString();
        String ID_no = id_NO.getText().toString();
        String Location = location.getText().toString();
        String email =Email.getText().toString();
        String password =Password.getText().toString();


        if (FirstName.isEmpty()) {
            firstName.setError("First Name is empty");
            Toast.makeText(RegActivity.this, "First Name is empty", Toast.LENGTH_SHORT).show();

            return false;
        }

        else if (Location.isEmpty()) {
            location.setError("Location is Empty");
            Toast.makeText(RegActivity.this, "Location is Empty", Toast.LENGTH_SHORT).show();

            return false;
        }

        else if (LastName.isEmpty()) {
            lastName.setError("Last Name is Empty");
            Toast.makeText(RegActivity.this, "Last Name is Empty", Toast.LENGTH_SHORT).show();

            return false;
        }
        else if (PhoneNumber.isEmpty()) {
            phoneNumber.setError("Mobile Number is Empty");
            Toast.makeText(RegActivity.this, "Mobile Number is Empty", Toast.LENGTH_SHORT).show();

            return false;
        }

        else if (ID_no.isEmpty()) {
            id_NO.setError("ID number Required");
            Toast.makeText(RegActivity.this, "ID number Required.", Toast.LENGTH_SHORT).show();

            return false;
        }

        else if (email.isEmpty()) {
            Email.setError("Email is empty");
            Toast.makeText(RegActivity.this, "Email is empty.", Toast.LENGTH_SHORT).show();
            return false;

        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Email.setError("Please enter a Valid email");
            Toast.makeText(RegActivity.this, "Please enter a Valid email.", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (password.isEmpty()) {
            Password.setError("ID number Required");
            Toast.makeText(RegActivity.this, "ID number Required", Toast.LENGTH_SHORT).show();
            return false;
        }
//        else if (Con_Password == Password) {
//            con_Password.setError("Password Don't Match");
//            return false;
//        }
        else {
            firstName.setError(null);
            location.setError(null);
            lastName.setError(null);
            phoneNumber.setError(null);
            id_NO.setError(null);
            Password.setError(null);
//            con_Password.setError(null);
            Email.setError(null);
            return true;
        }

    }
}
