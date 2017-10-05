package osakacityfire.kinentai;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by tadakazu on 2016/08/24.
 */
public class GuideActivity extends AppCompatActivity {
    protected GuideActivity mActivity = null;
    protected View mView = null;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        mActivity = this;
        mView = this.getWindow().getDecorView();
        setContentView(R.layout.activity_guide);

        //参集アプリの使用目的ボタン作成
        mView.findViewById(R.id.btnGuidePurpose).setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v){
                showPurpose();
            }
        });
        //以下、リストビューを使って選択ボタン（のように見せる）作成
        ListView mListView = (ListView)findViewById(R.id.listGuide);
        //データを準備
        String[] title = {"緊援隊出場基準確認ボタン","情報確認ボタン"};
        String[] content = {"事象に応じた招集命令の確認方法","気象情報、ライフライン等情報の確認方法"};
        String[] number = {"１","２"};
        ArrayList<Guide> guides = new ArrayList<>();
        for (int i=0; i<title.length; i++){
            Guide guide = new Guide();
            guide.setTitle(title[i]);
            guide.setContent(content[i]);
            guide.setNumber(number[i]);
            guide.setIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_navigate_next_black_36dp));
            guides.add(guide);
        }

        UserAdapter adapter = new UserAdapter(this, 0, guides);
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                switch (position){
                    case 0:
                        showGuide4();
                        break;
                    case 1:
                        showGuide5();
                        break;
                    default:
                        break;
                }
            }
        });

        //復帰用ボタン
        mView.findViewById(R.id.btnKinentai).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(mActivity, KinentaiActivity.class);
                startActivity(intent);
            }
        });
    }

    public class UserAdapter extends ArrayAdapter<Guide> {
        private LayoutInflater layoutInflater;

        public UserAdapter(Context c, int id, ArrayList<Guide> guides){
            super(c, id, guides);
            this.layoutInflater = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            if (convertView == null){
                convertView = layoutInflater.inflate(R.layout.list_guide, parent, false);
            }

            Guide guide = (Guide)getItem(position);
            ((TextView)convertView.findViewById(R.id.guideTitle)).setText(guide.getTitle());
            ((TextView)convertView.findViewById(R.id.guideContent)).setText(guide.getContent());
            ((TextView)convertView.findViewById(R.id.guideNumber)).setText(guide.getNumber());
            ((ImageView)convertView.findViewById(R.id.guideArrow)).setImageBitmap(guide.getIcon());

            return convertView;
        }
    }

    public class Guide {
        private String title;
        private String content;
        private String number;
        private Bitmap icon;

        public void setTitle(String s){
            this.title = s;
        }

        public String getTitle(){
            return title;
        }

        public void setContent(String s){
            this.content = s;
        }

        public String getContent(){
            return content;
        }

        public void setNumber(String s){
            this.number = s;
        }

        public String getNumber(){
            return number;
        }

        public void setIcon(Bitmap icon){
            this.icon = icon;
        }

        public Bitmap getIcon(){
            return icon;
        }
    }

    //参集アプリの使用目的
    private void showPurpose(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //テキストファイル読み込み
        InputStream is = null;
        BufferedReader br = null;
        String text = "";
        try {
            try {
                //assetsフォルダ内のテキスト読み込み
                is = getAssets().open("purpose.txt");
                br = new BufferedReader(new InputStreamReader(is));
                //１行づつ読み込み、改行追加
                String str;
                while((str = br.readLine()) !=null){
                    text += str + "\n";
                }
            } finally {
                if (is != null) is.close();
                if (br != null) br.close();
            }
        } catch (Exception e) {
            //エラーメッセージ
            Toast.makeText(this, "テキスト読込エラー", Toast.LENGTH_LONG).show();
        }
        builder.setMessage(text);
        builder.setNegativeButton("閉じる", null);
        builder.setCancelable(true);
        builder.create();
        builder.show();
    }

    //1　非常招集確認ボタン
    private void showGuide4() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //カスタムビュー設定
        LayoutInflater inflater = (LayoutInflater)this.getSystemService(LAYOUT_INFLATER_SERVICE);
        final View layout = inflater.inflate(R.layout.guide44, (ViewGroup)findViewById(R.id.guide44));
        builder.setView(layout);
        builder.setNegativeButton("閉じる",null);
        builder.setCancelable(true);
        builder.create();
        builder.show();
    }

    //2　情報確認
    private void showGuide5(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //カスタムビュー設定
        LayoutInflater inflater = (LayoutInflater)this.getSystemService(LAYOUT_INFLATER_SERVICE);
        final View layout = inflater.inflate(R.layout.guide5, (ViewGroup)findViewById(R.id.guide5));
        builder.setView(layout);
        builder.setNegativeButton("閉じる",null);
        builder.setCancelable(true);
        builder.create();
        builder.show();
    }
}
