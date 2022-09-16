package olalatheexpert.cloudstore.purchasepackagehere;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.cloudstore.purchasepackagehere.R;

import olalatheexpert.cloudstore.utils.Prefs;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.ConsumeParams;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.SkuDetails;
import com.android.billingclient.api.SkuDetailsParams;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    BillingClient billingClient;
    TextView clicks;
    Button btn_5,btn_15,btn_25, btn_35,btn_45,btn_55,btn_65,btn_75,btn_85;

    Prefs prefs ;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prefs = new Prefs(this);

        initViews();


        billingClient = BillingClient.newBuilder(getApplicationContext())
                .setListener((billingResult, list) -> {
                    if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK && list != null) {
                        for (Purchase purchase : list) {
                            Log.d("TestA2d",""+list);
                            verifyPayment(purchase);
                        }
                    }

                })
                .enablePendingPurchases()
                .build();

        connectGooglePlayBilling();

    }

    @SuppressLint("SetTextI18n")
    private void initViews() {

        clicks = findViewById(R.id.clicks);

        btn_5 = findViewById(R.id.btn_10);
        btn_15 = findViewById(R.id.btn_20);
        btn_25 = findViewById(R.id.btn_30);
        btn_35 =findViewById(R.id.btn_40);
        btn_45=findViewById(R.id.btn_50);
        btn_55=findViewById(R.id.btn_60);
        btn_65=findViewById(R.id.btn_70);
        btn_75=findViewById(R.id.btn_80);
        btn_85=findViewById(R.id.btn_90);

        clicks.setText("You have "+prefs.getInt("clicks",0)+ " click(s)");
    }

    void connectGooglePlayBilling() {

        billingClient.startConnection(new BillingClientStateListener() {
            @Override
            public void onBillingServiceDisconnected() {
                connectGooglePlayBilling();
            }

            @Override
            public void onBillingSetupFinished(@NonNull BillingResult billingResult) {
                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                    getProducts();
                }

            }
        });
    }


    void getProducts() {
        List<String> products = new ArrayList<>();
        products.add("click_1");
        products.add("click_2");
        products.add("click_3");
        products.add("click_4");
        products.add("click_5");
        products.add("click_6");
        products.add("click_7");
        products.add("click_8");
        products.add("click_9");
        SkuDetailsParams.Builder params = SkuDetailsParams.newBuilder();
        params.setSkusList(products).setType(BillingClient.SkuType.INAPP);

        billingClient.querySkuDetailsAsync(params.build(), (billingResult, list) -> {
            if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK && list != null) {


                for (SkuDetails skuDetails : list) {
                    if (skuDetails.getSku().equals("click_1")) {
                        btn_5.setText("Pick Package ("+skuDetails.getPrice()+")");
                        btn_5.setOnClickListener(view -> {
                            launchPurchaseFlow(skuDetails);
                        });
                    } else if (skuDetails.getSku().equals("click_2")) {
                        btn_15.setText("Pick Package ("+skuDetails.getPrice()+")");
                        btn_15.setOnClickListener(view -> {
                            launchPurchaseFlow(skuDetails);
                        });
                    } else if (skuDetails.getSku().equals("click_3")) {
                        btn_25.setText("Pick Package ("+skuDetails.getPrice()+")");
                        btn_25.setOnClickListener(view -> {
                            launchPurchaseFlow(skuDetails);
                        });
                    }else if (skuDetails.getSku().equals("click_4")){
                        btn_35.setText("Pick Package ("+skuDetails.getPrice()+")");
                        btn_35.setOnClickListener(view -> {
                            launchPurchaseFlow(skuDetails);
                        });
                    }else if (skuDetails.getSku().equals("click_5")){
                        btn_45.setText("Pick Package ("+skuDetails.getPrice()+")");
                        btn_45.setOnClickListener(view -> {
                            launchPurchaseFlow(skuDetails);
                        });
                    }else if (skuDetails.getSku().equals("click_6")){
                        btn_55.setText("Pick Package ("+skuDetails.getPrice()+")");
                        btn_55.setOnClickListener(view -> {
                            launchPurchaseFlow(skuDetails);
                        });
                    }else if (skuDetails.getSku().equals("click_7")){
                        btn_65.setText("Pick Package ("+skuDetails.getPrice()+")");
                        btn_65.setOnClickListener(view -> {
                            launchPurchaseFlow(skuDetails);
                        });
                    }else if (skuDetails.getSku().equals("click_8")){
                        btn_75.setText("Pick Package ("+skuDetails.getPrice()+")");
                        btn_75.setOnClickListener(view -> {
                            launchPurchaseFlow(skuDetails);
                        });
                    }else if (skuDetails.getSku().equals("click_9")){
                        btn_85.setText("Pick Package ("+skuDetails.getPrice()+")");
                        btn_85.setOnClickListener(view -> {
                            launchPurchaseFlow(skuDetails);
                        });
                    }
                }

            }
        });

    }

    void launchPurchaseFlow(SkuDetails skuDetails) {
        BillingFlowParams billingFlowParams = BillingFlowParams.newBuilder()
                .setSkuDetails(skuDetails)
                .build();
        billingClient.launchBillingFlow(this, billingFlowParams);
    }

    void verifyPayment(Purchase purchase) {


        ConsumeParams consumeParams = ConsumeParams.newBuilder()
                .setPurchaseToken(purchase.getPurchaseToken())
                .build();

        billingClient.consumeAsync(ConsumeParams.newBuilder().setPurchaseToken(purchase.getPurchaseToken()).build(), (billingResult, s) -> {
            if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                Log.d("TestA2d","Item consumed");
                Toast.makeText(MainActivity.this, "Item Consumed", Toast.LENGTH_SHORT).show();

                if (purchase.getSkus().get(0).equals("click_1")) {
                    updateClicks(1);
                } else if (purchase.getSkus().get(0).equals("click_2")) {
                    updateClicks(2);
                } else if (purchase.getSkus().get(0).equals("click_3")) {
                    updateClicks(3);
                }else if (purchase.getSkus().get(0).equals("click_4")){
                    updateClicks(4);
                }else if (purchase.getSkus().get(0).equals("click_5")){
                    updateClicks(5);
                }else if (purchase.getSkus().get(0).equals("click_6")){
                    updateClicks(6);
                }else if (purchase.getSkus().get(0).equals("click_7")){
                    updateClicks(7);
                }else if (purchase.getSkus().get(0).equals("click_8")){
                    updateClicks(8);
                }else if (purchase.getSkus().get(0).equals("click_9")){
                    updateClicks(9);
                }

            }
        });
    }

    @SuppressLint("SetTextI18n")
    void updateClicks(int v) {
        prefs.setInt("clicks",v);
        clicks.setText("You have choose "+prefs.getInt("clicks",0)+ " package(s)");
    }

    @SuppressLint({"NotifyDataSetChanged", "SetTextI18n"})
    @Override
    protected void onResume() {
        super.onResume();
        clicks.setText("You have choose "+prefs.getInt("clicks",0)+ " package(s)");
        billingClient.queryPurchasesAsync(
                BillingClient.SkuType.INAPP,
                (billingResult, list) -> {
                    if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                        for (Purchase purchase : list) {
                            if (purchase.getPurchaseState() == Purchase.PurchaseState.PURCHASED && !purchase.isAcknowledged()) {
                                verifyPayment(purchase);
                            }
                        }
                    }
                }
        );
    }

}