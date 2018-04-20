/**
 * IMPORTANT: Add your package below. Package name can be found in the project's AndroidManifest.xml file.
 * This is the package name our example uses:
 *
 * package com.example.android.justjava; 
 *
 */

package com.example.android.justjava;
import java.text.NumberFormat;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.BoolRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int numberOfCoffee = 0;
    int price=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {

        CheckBox whippedCreamCheckbox= (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        CheckBox chocolateCheckbox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckbox.isChecked();
        boolean hasWhippedCream = whippedCreamCheckbox.isChecked();
        EditText nameField=(EditText) findViewById(R.id.name_field);
        String name=nameField.getText().toString();
        int price = calculatePrice(hasWhippedCream,hasChocolate);
        String priceMessage = createOrderSummary(5,hasWhippedCream,hasChocolate,name) ;

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java App");
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

        displayMessage(priceMessage);

       }

    public void increment(View view) {
        numberOfCoffee++;
        if(numberOfCoffee>100) {
            Toast.makeText(getApplicationContext(), "You cannot order more than 100 coffee",
                    Toast.LENGTH_SHORT).show();
            numberOfCoffee = 100;
        }
        displayQuantity(numberOfCoffee);
    }

    public void decrement(View view) {
         numberOfCoffee--;
        if(numberOfCoffee<1) {
            Toast.makeText(getApplicationContext(), "You cannot order less than 1 coffee",
                    Toast.LENGTH_SHORT).show();
            numberOfCoffee = 1;
        }
        displayQuantity(numberOfCoffee);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }

    private int calculatePrice(boolean hwc,boolean hc) {
        int p1=0;int p2=0;
         if(hwc) {
              p1= 1;
         }
        if(hc) {
             p2 = 2;
        }
         price = numberOfCoffee * (5+p1+p2);
        return price;
    }

    public String createOrderSummary(int costPerCup,boolean addWhippedCream,boolean hasChocolate,String name) {
        String message= "Name : " + name + "\nQauntity : " + numberOfCoffee + "\nExtra Whipped Cream : "+ addWhippedCream +"\nExtra Chocolate : " + hasChocolate +"\nTotal : $" + price + "\nThankYou!";
        return message;
    }




}