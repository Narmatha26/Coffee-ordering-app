package android.example.coffeeshop;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    /**
     * this is method is called when the order button is clicked
     */
    public void submitOrder(View view) {
        //to get the name of the customer
        EditText name = (EditText) findViewById(R.id.customer_name);
        String customerName = name.getText().toString();

        //to figure out if the customer wants whipped cream topping
        CheckBox whippedCreamCheckBox = ((CheckBox) findViewById(R.id.whipped_cream_checkbox));
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        //to figure out if the customer wants chocolate topping
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();

        //the calculatePrice Method and priceMessage method is called
        int price = calculatePrice(hasWhippedCream, hasChocolate);
        String priceMessage = createOrderSummary(price, hasWhippedCream, hasChocolate, customerName);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Coffee order for " + customerName);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * @param addWhippedCream is whether or not user wants this topping
     * @param addChocolate    is whether or not user wants this topping
     * @return total price
     * calculates the price of the order
     */
    private int calculatePrice(boolean addWhippedCream, boolean addChocolate) {
        int basePrice = 5;
        if (addWhippedCream) {
            basePrice = basePrice + 1;
        }
        if (addChocolate) {
            basePrice = basePrice + 2;
        }

        return basePrice * quantity;
    }

    /**
     * @param price           is passed as input
     * @param addWhippedCream is whether or not user wants this topping
     * @param addChocolate    is whether or not user wants this topping
     * @param customerName    to get the name of the customer
     * @return text summary
     * this gives the order summary
     */
    private String createOrderSummary(int price, boolean addWhippedCream, boolean addChocolate, String customerName) {
        String priceMessage = "Name: " + customerName;
        priceMessage += "\nAdd whipped cream? " + addWhippedCream;
        priceMessage += "\nAdd chocolate? " + addChocolate;
        priceMessage += "\nQuantity: " + quantity + "\nTotal price: " + "$" + price;
        priceMessage += " \nThank you!";
        return priceMessage;
    }


    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * this is method is called when the plus button is clicked
     */
    public void increment(View view) {
        if (quantity == 100) {
            //show an error message
            Toast.makeText(this, "You cannot order more than 100 cups of coffee", Toast.LENGTH_SHORT).show();
            //exit this method
            return;
        }
        quantity = quantity + 1;
        displayQuantity(quantity);
    }

    /**
     * this is method is called when the minus button is clicked
     */
    public void decrement(View view) {
        if (quantity == 1) {
            //show an error message
            Toast.makeText(this, "You cannot order less than 1 cups of coffee", Toast.LENGTH_SHORT).show();
            //exit this method
            return;
        }
        quantity = quantity - 1;
        displayQuantity(quantity);
    }


}
