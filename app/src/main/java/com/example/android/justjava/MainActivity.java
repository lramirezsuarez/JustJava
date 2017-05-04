package com.example.android.justjava;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    TextView txtOrder;
    EditText txtName;
    int iCant = 0, iPrecio = 0;
    String nombre="", priceMessage="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtOrder = (TextView) findViewById(R.id.quantity_text_view);
        txtName = (EditText) findViewById(R.id.txtName);


    }

    public void increment(View view) {
        iCant++;
        if (iCant >= 100) {
            Toast.makeText(MainActivity.this, "You can't order more than 100.", Toast.LENGTH_SHORT).show();
            iCant = 100;
        }
        display(iCant);
    }

    public void decrement(View view) {
        iCant--;
        if (iCant < 1) {
            Toast.makeText(MainActivity.this, "You can't order less than 0.", Toast.LENGTH_SHORT).show();
            iCant = 1;
        }
        display(iCant);
    }

    public void submitOrder(View view) {
        nombre = txtName.getText().toString();
        if (nombre.equals("")) {
            Toast.makeText(MainActivity.this, "Escriba su nombre por favor.", Toast.LENGTH_SHORT).show();
        }
        CheckBox hasWhippedCream = (CheckBox) findViewById(R.id.checkWhipped);
        CheckBox hasChocolate = (CheckBox) findViewById(R.id.checkChocolate);
        boolean hasWhipped, chocolate;
        hasWhipped = hasWhippedCream.isChecked();
        chocolate = hasChocolate.isChecked();
        calculatePrice(hasWhipped, chocolate);
        priceMessage = createOrderSummary(hasWhipped, chocolate, nombre);
        displayMessage(priceMessage);
    }


    public void composeEmail(View view) {
        String subject = getResources().getString(R.string.asunto)+nombre;
        String mensaje = priceMessage;
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        //intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, mensaje);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }



    public String createOrderSummary(boolean cream, boolean choco, String nombre) {
        String msgOrderSummary = getString(R.string.name)+" " + nombre;
        msgOrderSummary += "\n"+ getString(R.string.sumQuantity)+" " + iCant;
        if (cream) {
            msgOrderSummary += "\n" + getString(R.string.adCrema)+" " + getString(R.string.yes);
        } else {
            msgOrderSummary += "\n" + getString(R.string.adCrema)+ " " + getString(R.string.no);
        }
        if (choco) {
            msgOrderSummary += "\n"  + getString(R.string.adChoco)+ " " + getString(R.string.yes);
        } else {
            msgOrderSummary += "\n" + getString(R.string.adChoco)+ " " + getString(R.string.no);
        }
        msgOrderSummary += "\n" + getString(R.string.total)+" $" + iPrecio;
        msgOrderSummary += "\n" + getString(R.string.thanks);
        return msgOrderSummary;
    }

    private int calculatePrice(boolean cream, boolean choco) {
        iPrecio = 5;
        if (cream && choco) {
            iPrecio += 5;
        } else if (cream) {
            iPrecio += 2;
        } else if (choco) {
            iPrecio += 3;
        }
        iPrecio = iPrecio * iCant;
        return iPrecio;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        txtOrder.setText("" + number);
    }


    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView priceTextView = (TextView) findViewById(R.id.txtOrderSumary);
        priceTextView.setText(message);
    }
}
