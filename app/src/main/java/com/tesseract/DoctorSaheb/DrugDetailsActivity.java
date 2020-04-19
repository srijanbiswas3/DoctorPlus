package com.tesseract.DoctorSaheb;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class DrugDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drug_details);

        Intent intent = getIntent();

        String message=intent.getStringExtra("EXTRA_MESSAGE");
        TextView medName= findViewById(R.id.medName);
        TextView medGenName= findViewById(R.id.genericTxt);
//        TextView medAbout= findViewById(R.id.aboutTxt);
        TextView medUse= findViewById(R.id.useTxt);
        TextView medSideEffects= findViewById(R.id.sideEffectsTxt);

        medName.setText(message);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        toolbar.setTitle(message);

        switch(message) {

            case "Abilify":
                medName.setText(message);
                medGenName.setText("Acetaminophen");
                medUse.setText("Abilify is used to treat the symptoms of psychotic conditions such as manic depression and is also used together with other medicines to treat major depressive disorder in adults.");
                medSideEffects.setText("Allergic reactions include hives, difficult breathing, swelling of your face, lips, tongue, or throat.");
                break;

            case "Acetaminophen":
                medName.setText(message);
                medGenName.setText("Acetaminophen");
                medUse.setText("Acetaminophen is a pain reliever and fever reducer. Common conditions that acetaminophen treats include headache, muscle aches, arthritis, backache, toothaches, colds.");
                medSideEffects.setText("Common side effects include nausea,vomiting,headache and insomnia.");
                break;

            case "Acyclovir":
                medName.setText(message);
                medGenName.setText("Acyclovir");
                medUse.setText("Acyclovir is an antiviral drug. Acyclovir is used to treat infections caused by herpes viruses, such as genital herpes, cold sores, shingles, and chicken pox, as well as varicella (chickenpox), and cytomegalovirus.");
                medSideEffects.setText("Common side effects include nausea,vomitting,diarrhorea,general ill feeling,headache.");
                break;

            case "Acetaminophen and Hydrocodone":
                medName.setText(message);
                medGenName.setText("Acetaminophen and Hydrocodone");
                medUse.setText("Acetaminophen and hydrocodone is a combination medicine used to relieve moderate to moderately severe pain.");
                medSideEffects.setText("Allergic reactions include hives,difficulty in breathing,swelling of your face,lips,throat and tongue.");
                break;

            case "Actos":
                medName.setText(message);
                medGenName.setText("Pioglitazone");
                medUse.setText("It works by helping to restore your body's proper response to insulin, thereby lowering your blood sugar.");
                medSideEffects.setText("Sore throat, muscle pain, weight gain, or tooth problems may occur.\n");
                break;

            case "Belsomra":
                medName.setText(message);
                medGenName.setText("Suvorexant ");
                medUse.setText("Belsomra (suvorexant) is a sleep medicine that helps regulate your sleep and wake cycle.");
                medSideEffects.setText("Allergic reactions due to this drug include hives,difficulty in breathing,swelling of your face,lips,throat and tongue.");
                break;

            case "Benadryl":
                medName.setText(message);
                medGenName.setText("Diphenhydramine");
                medUse.setText("Benadryl is used to treat sneezing, runny nose, watery eyes, hives, skin rash, itching, and other cold or allergy symptoms.");
                medSideEffects.setText("Side effects may include dizziness, drowsiness, loss of coordination,dry mouth, nose, or throat,constipation, upset stomach,\n" +
                        "dry eyes, blurred vision,day-time drowsiness or \"hangover\" feeling after night-time use.");
                break;

            case "Benazepril":
                medName.setText(message);
                medGenName.setText("Benazepril");
                medUse.setText("Benazepril is an ACE inhibitor that is used to treat high blood pressure (hypertension). Lowering blood pressure may lower your risk of a stroke or heart attack.");
                medSideEffects.setText("Common side effects cough,headache  and liver problems");
                break;

            case "Benicar":
                medName.setText(message);
                medGenName.setText("Olmesartan");
                medUse.setText("Benicar is used to treat high blood pressure (hypertension) in adults and children who are at least 6 years old. Benicar is sometimes given together with other blood pressure medications.");
                medSideEffects.setText("Side effects include dizziness,high level of potassium,fast heart rate,swelling in hands and feet.");
                break;

            case "Carbamazepine":
                medName.setText(message);
                medGenName.setText("Carbamazepine");
                medUse.setText("Carbamazepine is used to treat bipolar disorder.It works by decreasing nerve impulses that cause seizures and nerve pain, such as trigeminal neuralgia and diabetic neuropathy.");
                medSideEffects.setText("Side effects include dizziness,nausea,vomitting,problems with walking,loss of coordination.");
                break;

            case "":
                medName.setText(message);
                medGenName.setText("");
                medUse.setText("");
                medSideEffects.setText("");
                break;

            case "Cardizem":
                medName.setText(message);
                medGenName.setText("Diltiazem");
                medUse.setText("Cardizem (diltiazem) is a calcium channel blocker. It works by relaxing the muscles of your heart and blood vessels. Cardizem is used to treat hypertension (high blood pressure).");
                medSideEffects.setText("Dizziness,weakness,headache,nausea,rash.");
                break;

            case "Carvedilol":
                medName.setText(message);
                medGenName.setText("Carvedilol");
                medUse.setText("Carvedilol is used to treat heart failure and hypertension (high blood pressure). It is also used after a heart attack that has caused your heart not to pump as well.");
                medSideEffects.setText("Weakness,diarrhorea,dizziness,dry eyes,tired feeling,weight gain.");
                break;

            case "Demerol":
                medName.setText(message);
                medGenName.setText("Meperidine");
                medUse.setText("Demerol is a strong prescription pain medicine that is used to manage the relief short-term pain, when other pain treatments such as non-opioid pain medicines do not treat your pain well enough or you cannot tolerate them.");
                medSideEffects.setText("Dizziness, drowsiness, headache, nausea, vomiting or sweating.");
                break;

            case "Desloratadine":
                medName.setText(message);
                medGenName.setText("Desloratadine");
                medUse.setText("Desloratadine is an antihistamine that is used to treat the symptoms of allergies, such as sneezing, watery eyes, itching, and runny nose. Desloratadine is also used to treat skin hives and itching in people with chronic skin reactions.");
                medSideEffects.setText("Dry mouth, sore throat,muscle pain, drowsiness, tiredness or menstrual pain.");
                break;

            case "Darvocet":
                medName.setText(message);
                medGenName.setText("Propoxyphene and acetaminophen");
                medUse.setText("Darvocet contains a combination of propoxyphene and acetaminophen.Darvocet is used to relieve mild to moderate pain with or without fever.");
                medSideEffects.setText("Agitation, restlessness, anxiety, insomnia, tremor, tachycardia, hallucinations, psychosis, abdominal cramps, vomiting, sweating, and seizures.");
                break;

            case "Eliquis":
                medName.setText(message);
                medGenName.setText("Apixaban");
                medUse.setText("Eliquis is used to lower the risk of stroke or a blood clot in people with a heart rhythm disorder called atrial fibrillation. Eliquis is also used to lower the risk of forming a blood clot in the legs and lungs of people who have just had hip or knee replacement surgery.");
                medSideEffects.setText("Symptoms of a spinal blood clot: back pain, numbness or muscle weakness in your lower body, or loss of bladder or bowel control.");
                break;

            case "Ecotrin and MDMA":
                medName.setText(message);
                medGenName.setText("Aspirin");
                medUse.setText("Aspirin is used to reduce fever and relieve mild to moderate pain from conditions such as muscle aches, toothaches, common cold, and headaches.");
                medSideEffects.setText("Dyspepsia, epigastric discomfort, heartburn, and nausea.");
                break;


        }


    }
}
