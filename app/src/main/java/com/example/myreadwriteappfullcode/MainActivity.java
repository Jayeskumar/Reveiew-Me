package com.example.myreadwriteappfullcode;

//Projects in Series 2:
//1. Build an App in Android Studio using Resources
//2. Build an App in Android Studio using Static Files
//3. Build an App in Android Studio using Read - Write
//4. Build an App in Android Studio using onTouch
//5. Build an App in Android Studio using Activities

//***THIS FILE WILL SHOW ERRORS UNTIL YOU HAVE COMPLETED THE TASKS YOU ARE REQUIRED TO DO.  WHEN YOU HAVE
//SUCCESSFULLY FINISHED THE TASK THE ERRORS SHOULD ALL BE GONE.***

//androidx.appcompat.app.AppCompatActivity and android.os.Bundle are put in by default when basic
//activity selected when new project is created in Android Studio. All of the other imports where
//put in manually later during the making of the project.
//START
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Context;
import android.content.res.Resources;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
//END

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    //The strings below are used for persistent storage - remembering a user selections after the user
    //closes the app. Persistent storage is looked at in much more detail in another project, "Build
    //a Persistent Storage App in Android Studio", and we will not be looking at it here. You do not
    //have to do anything with this code.
    //START
    public static final String MYPREFS = "mySharedPreferences";
    private String savedAuthor;
    //END

    //Creating Java equivalent objects for the widgets in our user interface which we created in xml
    //that we want to interact with (change) or give values to in some way.
    //START
    private TextView txtPickedAuthor;
    private Spinner sprPickedAuthor;
    private ImageView imgPickedAuthor;
    private TextView txtPickedAuthorBook;
    private TextView txtPickedAuthorQuote;
    private TextView txtReview;
    private EditText edtReview;
    private Button btnReadReview;
    private Button btnSubmitReview;
    private Button btnSubmit;
    private Button btnCancel;
    private Button btnNoReview;
    //END

    //We are creating six String arrays to store the quotes from the relevant text files when the
    //data is read from the text files.
    //We are creating five Strings to store the reviews from the relevant text files when the data
    //is read from the text file.
    //We create one String to hold the review of the author currently selected in our drop down. The
    //text show in our txtReview TextView is taken from this String.
    //START
    String[] defaultquote;
    String[] oscarwilde;
    String[] cslewis;
    String[] bramstoker;
    String[] jonathanswift;
    String[] samuelbeckett;
    String oscarwildereview;
    String cslewisreview;
    String bramstokerreview;
    String jonathanswiftreview;
    String samuelbeckettreview;
    String theReview;
    //END

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Connect our java objects to the appropriate widget in our user interface
        //START
        txtPickedAuthor = findViewById(R.id.txtPickedAuthor);
        sprPickedAuthor = findViewById(R.id.sprAuthor);
        imgPickedAuthor = findViewById(R.id.imgAuthor);
        txtPickedAuthorBook = findViewById(R.id.txtPickedAuthorBook);
        txtPickedAuthorQuote = findViewById(R.id.txtPickedAuthorQuote);
        txtReview = findViewById(R.id.txtReview);
        edtReview = findViewById(R.id.edtReview);
        btnReadReview = findViewById(R.id.btnReadReview);
        btnSubmitReview = findViewById(R.id.btnSubmitReview);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnCancel = findViewById(R.id.btnCancel);
        btnNoReview = findViewById(R.id.btnNoReview);
        //END

        //Read the information from the six text files that we put in the raw directory and store the
        //information inside the appropriate String[] that we created. We identify a specific file in
        //the raw directory and send that file id to the loadQuotations method and then puts the information
        //returned back from the loadQuotations method into a specific String[] that we created earlier.
        //The loadQuotations method looks for the specific file, takes the data from inside the file,
        //and returns that data.
        //Read the information from the five text files in internal storage that are created by our code (if it does not
        //already exist) when the submit button is pressed for a review. We store the information inside
        //the appropriate String that we created. We send a file name to the loadReviews method and
        //then put put the information returned from the method into a specific String that we created
        //earlier. The loadReviews method looks for the specific file, takes the data from inside the file
        //and returns the data. Each author has their own file.
        //START
        defaultquote = loadQuotation(R.raw.defaultquote);
        oscarwilde = loadQuotation(R.raw.oscarwilde);
        cslewis = loadQuotation(R.raw.cslewis);
        bramstoker = loadQuotation(R.raw.bramstoker);
        jonathanswift = loadQuotation(R.raw.jonathanswift);
        samuelbeckett = loadQuotation(R.raw.samuelbeckett);
        oscarwildereview = loadReviews("oscarwildereview.txt");
        cslewisreview = loadReviews("cslewisreview.txt");
        bramstokerreview = loadReviews("bramstokerreview.txt");
        jonathanswiftreview = loadReviews("jonathanswiftreview.txt");
        samuelbeckettreview = loadReviews("samuelbeckettreview.txt");
        //END

        //The four lines below are setting an image called questionmark from the res/drawable
        //folder to our ImageView imgPickedAuthor, a string called pickanauthor from res/values/strings
        //to txtPickedAuthor, a string called whoistheauthor from res/values/strings to txtPickedAuthorBook,
        //and a string called defaultquote to txtPickedAuthorQuote.
        //START
        imgPickedAuthor.setImageResource(R.drawable.questionmark);
        txtPickedAuthor.setText(R.string.pickanauthor);
        txtPickedAuthorBook.setText(R.string.whoistheauthor);
        txtPickedAuthorQuote.setText(Arrays.toString(defaultquote));
        //END

        //The below two lines are using array from resources (res/values/strings.xml) to fill in the
        //pieces of information that will be inside our Spinner (author names).
        //You do not have to do anything with this code.
        //START
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.authors, R.layout.support_simple_spinner_dropdown_item);
        sprPickedAuthor.setAdapter(adapter);
        //END

        //For a Spinner you can not use an onClickListener like you would for a Button.  You must
        //use setOnItemSelectedListener where you set the instructions for what to do when a item in
        //the Spinner is selected. There are two parts of an item selected listener, what to do when
        //an item is selected and what to do when no item is selected.
        //START
        sprPickedAuthor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //This line is checking what item in the Spinner has been selected based on the
                //position (index number) of the item.
                Object author = parent.getItemAtPosition(position);
                //The position number (same as all arrays in Java) starts at index 0. So "Pick an author"
                //would be at position 0 but it is easier for us to picture in our brains if the first
                //equals 1, second equals 2 etc. To do this we create an int called pickedAuthor and
                //give it the value of the position number plus 1.
                int pickedAuthor = position + 1;
                //We take the the value in picked author, change it to a String and give it as the
                //value of savedAuthor for use in Persistent Storage (remembering the selection
                //when the user closes the app.
                savedAuthor = String.valueOf(author).toLowerCase();
                //We are calling a method called getPickedAuthorBookAndQuote and sending it the name
                //of the selected author.
                getPickedAuthorBooksAndQuote(savedAuthor);
                //We are using a switch statement to check what number we just saved in pickedAuthor
                //and then we are changing the image used in the ImageView imgPickedAuthor so that it
                //matches the author that is selected.
                switch (pickedAuthor) {
                    case 2:  imgPickedAuthor.setImageResource(R.drawable.oscarwilde);     break;
                    case 3:  imgPickedAuthor.setImageResource(R.drawable.cslewis);        break;
                    case 4:  imgPickedAuthor.setImageResource(R.drawable.bramstoker);     break;
                    case 5:  imgPickedAuthor.setImageResource(R.drawable.jonathanswift);  break;
                    case 6:  imgPickedAuthor.setImageResource(R.drawable.samuelbeckett);  break;
                    default :  imgPickedAuthor.setImageResource(R.drawable.questionmark); break;
                }

                //We are using a switch statement to check what number we just saved in pickedAuthor
                //and then we are changing the string used in txtPickedAuthor so that it matches the
                //author that is selected.
                switch (pickedAuthor) {
                    case 1: txtPickedAuthor.setText(R.string.pickanauthor);  break;
                    default : txtPickedAuthor.setText(String.valueOf(author)); break;
                }
                //We are using a switch statement to check what number we just saved in pickedAuthor
                //and then we are changing visibility of the Buttons btnReadReview and btnSubmitReview
                //so that they only appear on the screen if the user picks one of the authors.
                //author that is selected.
                switch (pickedAuthor) {
                    case 1: ; btnReadReview.setVisibility(View.GONE); btnSubmitReview.setVisibility(View.GONE); break;
                    default : btnReadReview.setVisibility(View.VISIBLE); btnSubmitReview.setVisibility(View.VISIBLE); break;
                }
            }

            //Nothing being selected in our Spinner is not an option as it will be at Pick an Author by
            //default if the user does nothing.  We are giving the instruction that if the user does
            //not actively (actually physically select an author instead of leaving it on Pick an Author
            //without pressing anything) select an author then the value in savedAuthor is set
            //to "" and the image in the ImageView imgPickedAuthor is set to the file called
            //questionmark in the res/drawable folder.
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                savedAuthor = "";
                imgPickedAuthor.setImageResource(R.drawable.questionmark);
            }
        });
        //END

        //We are calling the loadPreferences method for Persistent Storage. Persistent storage is looked at
        //in much more detail in another project "Build a Persistent Storage App in Android Studio"
        //and we will not be looking at it here. You do not have to do anything with this code.
        //START
        loadPreferences();
        //END

        //This is setting the instructions of what to do when the button "Read Review" is pressed. When
        //we put "this" inside the brackets it is telling the system to use the main onClick method
        //for the onClickListener for this button. You only have one onClick that is not nested ie
        //the onClick is inside the setOnClickListener.
        btnReadReview.setOnClickListener(this);

        //This is setting the instructions of what to do when the button "Submit Review" is pressed.
        //The only actions we are taking when this button is pressed is making some widgets not appear
        //on our app screen and making some widgets appear on our app screen.  This is just to tidy up
        //the screen so it is not too cluttered.
        btnSubmitReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sprPickedAuthor.setVisibility(View.GONE);
                txtPickedAuthorQuote.setVisibility(View.GONE);
                txtReview.setVisibility(View.GONE);
                btnReadReview.setVisibility(View.GONE);
                btnSubmitReview.setVisibility(View.GONE);
                btnNoReview.setVisibility(View.GONE);
                edtReview.setVisibility(View.VISIBLE);
                btnSubmit.setVisibility(View.VISIBLE);
                btnCancel.setVisibility(View.VISIBLE);
            }
        });

        //This is setting the instructions of what to do when the button "Submit" is pressed.
        //When this button is pressed we are using an if/else if/else structure to check what author
        //name is in the String called savedAuthor (this String is updated to the appropriate value
        //whenever the user selects something in our Spinner drop down menu). We are then taking what
        //the user has typed in the input box(EditText edtReview) and giving that value to the appropriate
        //review String (eg String oscarwildereview if the selected author is Oscar Wilde).
        //We are then making sure that our review String that is used by our TextView for reviews
        //(txtReview) is updated to match the currently selected author.
        //We then call the method called writeReview and send it two pieces of information. The first
        //piece of information is the String which contains the review we want the method to write to
        //a file, the second is the name of the file that we want the review written to.
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (savedAuthor.equals("oscar wilde")){
                    oscarwildereview = edtReview.getText().toString();
                    theReview = oscarwildereview;
                    try {
                        writeReview(theReview, "oscarwildereview.txt");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else if (savedAuthor.equals("cs lewis")){
                    cslewisreview = edtReview.getText().toString();
                    theReview = cslewisreview;
                    try {
                        writeReview(theReview, "cslewisreview.txt");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else if (savedAuthor.equals("bram stoker")){
                    bramstokerreview = edtReview.getText().toString();
                    theReview = bramstokerreview;
                    try {
                        writeReview(theReview, "bramstokerreview.txt");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else if (savedAuthor.equals("jonathan swift")){
                    jonathanswiftreview = edtReview.getText().toString();
                    theReview = jonathanswiftreview;
                    try {
                        writeReview(theReview, "jonathanswiftreview.txt");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else if (savedAuthor.equals("samuel beckett")){
                    samuelbeckettreview = edtReview.getText().toString();
                    theReview = samuelbeckettreview;
                    try {
                        writeReview(theReview, "samuelbeckettreview.txt");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else{

                }

                //We are now finished with what is inside our input box (EditText edtReview) so we
                //change its value to an empty String so that when we go to input a new review later,
                //the previously input review will not still be showing.
                //We then make changes to what widgets are visible and not visible to tidy up the app
                //screen so that it is not cluttered.
                edtReview.setText("");
                sprPickedAuthor.setVisibility(View.VISIBLE);
                txtPickedAuthorQuote.setVisibility(View.VISIBLE);
                btnReadReview.setVisibility(View.VISIBLE);
                btnSubmitReview.setVisibility(View.VISIBLE);
                edtReview.setVisibility(View.GONE);
                btnNoReview.setVisibility(View.GONE);
                btnSubmit.setVisibility(View.GONE);
                btnCancel.setVisibility(View.GONE);
                txtReview.setVisibility(View.GONE);
            }
        });

        //This is setting the instructions of what to do when the button "Cancel" is pressed.
        //The only actions we are taking when this button is pressed is making some widgets not appear
        //on our app screen and making some widgets appear on our app screen.  This is just to tidy up
        //the screen so it is not too cluttered.
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtReview.setVisibility(View.GONE);
                btnSubmit.setVisibility(View.GONE);
                btnCancel.setVisibility(View.GONE);
                txtReview.setVisibility(View.GONE);
                txtPickedAuthorQuote.setVisibility(View.VISIBLE);
                btnReadReview.setVisibility(View.VISIBLE);
                btnSubmitReview.setVisibility(View.VISIBLE);
                sprPickedAuthor.setVisibility(View.VISIBLE);
            }
        });

        //This is setting the instructions of what to do when the button "Nah" is pressed.
        //The only actions we are taking when this button is pressed is making some widgets not appear
        //on our app screen and making some widgets appear on our app screen.  This is just to tidy up
        //the screen so it is not too cluttered.
        btnNoReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                edtReview.setVisibility(View.GONE);
                btnSubmit.setVisibility(View.GONE);
                btnCancel.setVisibility(View.GONE);
                btnNoReview.setVisibility(View.GONE);
                txtReview.setVisibility(View.GONE);
                txtPickedAuthorQuote.setVisibility(View.VISIBLE);
                btnReadReview.setVisibility(View.VISIBLE);
                btnSubmitReview.setVisibility(View.VISIBLE);
                sprPickedAuthor.setVisibility(View.VISIBLE);
            }
        });

        //This code is just to tidy up the screen so it is not too cluttered.
        txtReview.setVisibility(View.GONE);
        edtReview.setVisibility(View.GONE);
        btnReadReview.setVisibility(View.GONE);
        btnSubmitReview.setVisibility(View.GONE);
        btnSubmit.setVisibility(View.GONE);
        btnCancel.setVisibility(View.GONE);
        btnNoReview.setVisibility(View.GONE);
    }

    //When our user clicks on read review, we check what author is inside the variable savedAuthor
    //(we update this variable with the name of the current selected author is our onItemSelected
    //method). Based on the current selected author we update a String called theReview with the review
    //for that author (if one exists). The String theReview is where the TextView txtReview gets its
    //sentence from.
    @Override
    public void onClick(View v) {
        if (savedAuthor.equals("oscar wilde")){
            theReview = oscarwildereview;
        }
        else if (savedAuthor.equals("cs lewis")){
            theReview = cslewisreview;
        }
        else if (savedAuthor.equals("bram stoker")){
            theReview = bramstokerreview;
        }
        else if (savedAuthor.equals("jonathan swift")){
            theReview = jonathanswiftreview;
        }
        else if (savedAuthor.equals("samuel beckett")){
            theReview = samuelbeckettreview;
        }
        else{
            theReview = getResources().getString(R.string.noreview);
        }

        //If what is now inside theReview is less than one character long (which would suggest there
        //is no review) then the value in theReview is updated to the value of a String in the strings
        //file called noreview (this sentence is - "No review yet!! Write one!!")
        if (theReview.length() < 1){
            theReview = getResources().getString(R.string.noreview);
        }

        //We update the sentence that is showing in our TextView txtReview and then use the setVisibility
        //function to make various widgets either appear or disappear from the screen of our app depending
        //on if they are relevant to what we want to see.
        txtReview.setText(theReview);
        txtReview.setVisibility(View.VISIBLE);
        btnNoReview.setVisibility(View.VISIBLE);
        sprPickedAuthor.setVisibility(View.GONE);
        txtPickedAuthorQuote.setVisibility(View.GONE);
        btnReadReview.setVisibility(View.GONE);
    }

    //This method checks what information is sent to it from the onSelectedItem method using if/else if
    // and then updates what is in txtPickedAuthorBook and txtPickedAuthorQuote to match the author
    //that is picked.
    public void getPickedAuthorBooksAndQuote(String s){
        String theAuthor = s;
        if (theAuthor.equals("oscar wilde")){
            txtPickedAuthorBook.setText(R.string.wildebook);
            txtPickedAuthorQuote.setText(Arrays.toString(oscarwilde));
        }
        else if (theAuthor.equals("cs lewis")){
            txtPickedAuthorBook.setText(R.string.lewisbook);
            txtPickedAuthorQuote.setText(Arrays.toString(cslewis));
        }
        else if (theAuthor.equals("bram stoker")){
            txtPickedAuthorBook.setText(R.string.stokerbook);
            txtPickedAuthorQuote.setText(Arrays.toString(bramstoker));
        }
        else if (theAuthor.equals("jonathan swift")){
            txtPickedAuthorBook.setText(R.string.swiftbook);
            txtPickedAuthorQuote.setText(Arrays.toString(jonathanswift));
        }
        else if (theAuthor.equals("samuel beckett")){
            txtPickedAuthorBook.setText(R.string.beckettbook);
            txtPickedAuthorQuote.setText(Arrays.toString(samuelbeckett));
        }
        else{
            txtPickedAuthorBook.setText(R.string.whoistheauthor);
            txtPickedAuthorQuote.setText(Arrays.toString(defaultquote));
        }
    }

    //The loadQuotations method looks for the specific file based on the id sent to this method from the
    //onCreate method (e.g. R.raw.defaultquote), takes the data from inside the file,
    //and returns that data to the place from which the method was called. How java reads from files is
    //not android specific and is beyond the scope of an android project.
    private String[] loadQuotation(int fileResourceID) {
        Resources myRes = getResources();
        InputStream is;
        InputStreamReader isr;
        BufferedReader br;
        ArrayList<String> quotes = new ArrayList<String>();
        String line;
        try {
            is = myRes.openRawResource( fileResourceID );
            isr = new InputStreamReader(is);
            br = new BufferedReader(isr);
            while ( (line = br.readLine()) != null ) {
                quotes.add(line);
            }
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
        }
        String[] returnStrings = new String[quotes.size()];
        returnStrings = quotes.toArray(returnStrings);
        return returnStrings;
    }

    //When our app starts up we are calling this method five times.  Each time we call it we send it
    //the name of a file that contains reviews (if it exists).  The method then sends back the text
    //from inside the file whose name we sent. That text is then put into the appropriate review string
    //that we created. For example, we call the file and send it the name "oscarwildereview.txt". This
    //method takes the review from inside that file (if it exists) and then sends (returns) the review
    //back so we can put the review inside our string oscarwildereview.
    private String loadReviews(String f){
        String review = "";
        FileInputStream fIn = null ;
        InputStreamReader isr = null;
        try{
            char[] inputBuffer = new char[1024];
            fIn = openFileInput(f);
            isr = new InputStreamReader(fIn);
            isr.read(inputBuffer);
            review = new String(inputBuffer);
            isr.close();
            fIn.close();
        }catch(IOException e){
            e.printStackTrace(System.err);
        }
        return review;
    }

    //When the user presses the "Submit" button this method is called.  This method is expecting to
    //receive two pieces of information (two Strings). The first String will be the review (r) that
    //we want the method to write to the file. The second String will be the name of the file (in
    //internal memory - not res / raw or external memory) that we want the review written to.
    //The method will check the path that where the file should be, it then checks if the file already
    //exits.
    //If a file with the given name does not exist then it will create it with that path.
    //When the method is sure the file is there then it writes the review it has been given to the file.
    //It then closes the open link to the file.
    //When it is finished writing to the file a Toast (pop up message will appear on the screen which
    //says "Review saved successfully!".
    private void writeReview(String r, String f) throws IOException {
        File file = new File(getFilesDir() ,f);
        if(!file.exists()){
            file.createNewFile();
        }else{

        }
        FileOutputStream fOut = null;
        OutputStreamWriter osw = null;
        try{
            fOut = openFileOutput(f, Context.MODE_PRIVATE);
            osw = new OutputStreamWriter(fOut);
            osw.write(r);
            osw.close();
            fOut.close();
            Toast.makeText(getBaseContext(), "Review saved successfully!",
                    Toast.LENGTH_SHORT).show();
        }catch(Exception e){
            e.printStackTrace(System.err);
        }
    }

    //You do not have to change any code below here.  The code below here is related to Persistent
    //Storage (Shared Preferences) and Menus. We have looked at persistent storage in the Build a
    //Persistent Storage App in Android Studio project on Coursera. If you would like to know more
    //about this you can go to that course. We have adjusted the value of the Strings beginning
    //with saved (savedAuthor) to match what the user has selected the last time they
    //closed the app. We are using a key value pairs below to remember what the value is and to update
    //the String value based on what is in the key value pair. In the loadPreferences() we are also
    //setting what is showing on our app for our Spinner to match what we have now put in savedAuthor.
    public void loadPreferences() {
        // Get the stored preferences
        int mode = Activity.MODE_PRIVATE;
        android.content.SharedPreferences mySharedPreferences = getSharedPreferences(MYPREFS, mode);
        //When you see words in grey like key: and defValue: below they are tags put in by android
        //studio.  These are not typed in by a person.
        //Retrieve the saved values and put them in savedMonthOfBirth, savedToggleButtonChoice, &
        //savedNumberPicked as appropriate.
        savedAuthor = mySharedPreferences.getString("author", "").toLowerCase();

        //We are using a switch statement to check what is the value that was saved in savedAuthor
        //and if what is saved is "oscar wilde", or "cs lewis", or "bram stoker", or "jonathan swift",
        //or "samuel beckett" then our app will go and select the appropriate author for us in the
        // Spinner sprPickedAuthor. If what is saved in savedAuthor does not match one of these then
        //the default of "Pick an Author" is selected. Remember that the position index starts at 0
        //so oscar wilde is at position 1, option "Pick an Author" is at position 0, etc.
        switch (savedAuthor) {
            case "oscar wilde":  sprPickedAuthor.setSelection(1);       break;
            case "cs lewis":  sprPickedAuthor.setSelection(2);          break;
            case "bram stoker":  sprPickedAuthor.setSelection(3);       break;
            case "jonathan swift":  sprPickedAuthor.setSelection(4);    break;
            case "samuel beckett":  sprPickedAuthor.setSelection(5);    break;
            default :  sprPickedAuthor.setSelection(0);                 break;
        }
    }

    //When the user closes the app we are checking what value is in savedAuthor, and we are saving
    //that information in a key value pair with the keys of "author".
    protected void savePreferences(){
        // Create or retrieve the shared preference object.
        int mode = Activity.MODE_PRIVATE;
        android.content.SharedPreferences mySharedPreferences = getSharedPreferences(MYPREFS, mode);
        // Retrieve an editor to modify the shared preferences.
        android.content.SharedPreferences.Editor editor = mySharedPreferences.edit();
        // Store data in the shared preferences object as key value pair.
        editor.putString("author", savedAuthor);
        // Commit the changes.
        editor.commit();
    }

    //This method just tells program what to do when the user closes the app (exit the app and call
    //the savePreferences method so that the relevant information is saved.
    @Override
    protected void onStop() {
        super.onStop();
        this.savePreferences();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_shared_preferences_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
