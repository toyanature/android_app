package com.example.puzzletest;

import java.util.Random;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.util.Log;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View.OnClickListener;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends Activity implements OnClickListener{

	private final int WC = ViewGroup.LayoutParams.WRAP_CONTENT; 
	private final int MP = ViewGroup.LayoutParams.MATCH_PARENT; 
	public static final int MENU_SELECT_RESET = 0;
	public static final int MENU_SELECT_NORMAL_RESET = 0;
	public static final int MENU_SELECT_RANDOM_RESET = 1;
	private int scoreCount = 0;
	private int colorFlg[][];
	private int initColorFlg[][];
	
	//private int colorList[][] = {{255,0,0},{0,255,0},{0,0,255}};
	private int colorList[];
	private int column_size;
	private int row_size;
	private int color_size;
	private LinearLayout mainLay;
	private Button btn[][];
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		
		initColorList();
		initRandom();
		//init(10,10,3);
		
		//setContentView(R.layout.activity_main);
		setContentView(mainLay);

    }
	
	//�����ݒ胁�\�b�h
	private void initReset(int col_num,int row_num,int color_num){
		column_size = col_num-1;
		row_size   = row_num-1;
		color_size = color_num-1;
		
		LinearLayout lineCol[];
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(MP, MP);
		//colorFlg = new int[col_num][row_num];
		lineCol = new LinearLayout[col_num];
		btn     = new Button[col_num][row_num];
		
		mainLay = new LinearLayout(this);
		mainLay.setLayoutParams(lp);
		mainLay.setOrientation(LinearLayout.VERTICAL);
		
		for(int i = 0;i < col_num;i++){
			lineCol[i] = new LinearLayout(this);
			lp.weight = 1;
			lineCol[i].setLayoutParams(lp);
			lineCol[i].setOrientation(LinearLayout.HORIZONTAL);
			
			for(int j=0;j < row_num;j++){
				
				btn[i][j] = new Button(this);
				btn[i][j].setLayoutParams(lp);
				btn[i][j].setBackgroundResource(colorList[initColorFlg[i][j]]);
				colorFlg[i][j] = initColorFlg[i][j];
				//btn[i][j].setBackgroundColor(
						//Color.rgb(colorList[myColor][0], colorList[myColor][1], colorList[myColor][2]));
				btn[i][j].setText(i+","+j);
				btn[i][j].setTextColor(Color.argb(0, 0, 0, 0));
				btn[i][j].setOnClickListener(this);
				lineCol[i].addView(btn[i][j]);
			}
			
			mainLay.addView(lineCol[i]);
			
		}
	}
	
	//BackgroundColor�̏����l��ݒ�@
	private void initColorList(){
		colorList = new int[3];
		colorList[0] = R.drawable.button_red; 
		colorList[1] = R.drawable.button_green; 
		colorList[2] = R.drawable.button_blue; 
	}
	
	private void setClearFlg(){
        Random rnd = new Random();
        column_size = rnd.nextInt(10);
        row_size = rnd.nextInt(10);
        color_size = rnd.nextInt(2);
        
        checkSize();
        //�ǂ̍s�ǂ̗�����񂨂���
        int pushBtn[][] = new int[column_size+1][row_size+1];
        
        colorFlg = new int[column_size+1][row_size+1];
        initColorFlg = new int[column_size+1][row_size+1];
        
        //�N���A���鎞�ɂ��낦��F���w��
        int clearColor =  rnd.nextInt(color_size);
        
        for(int i = 0;i <= column_size;i++){
        	for(int j=0;j <= row_size;j++){
        		colorFlg[i][j] = clearColor;
        		initColorFlg[i][j] = clearColor;
        		pushBtn[i][j] = rnd.nextInt(3);
        	}
        }

        //Log.e("size", "1:column:"+column_size+":"+"row:"+row_size+":"+"color:"+color_size);
		boolean resultFlg = true;
		int flgCount = 0;
		
		while(resultFlg == true){
        
		if(flgCount != 3){
        //�t���O�ύX
        for(int i = 0;i <= column_size;i++){
        	for(int j=0;j <= row_size;j++){
        		for(int k=0;k <= pushBtn[i][j]; k++){
        			changeColorIdInit(i,j);
        			//Log.e("size", i+":"+j+":"+k);
        		}
        		//��蒼���̂��߂ɍĐ������Ă���
        		pushBtn[i][j] = rnd.nextInt(3);
        	}
        }   
        //�����l�ƈ���Ă��邩�ǂ����m�F�@�����Ă�Ȃ������x��蒼��
		int flgCheck = colorFlg[0][0];
		
		for(int i = 0;i <= column_size;i++){
			for(int j = 0; j <= row_size; j++){
				if(colorFlg[i][j] != flgCheck){
					resultFlg = false;
					break;
				}
			}
		}
		
		}else{
	        for(int i = 0;i <= column_size;i++){
	        	changeColorIdInit(i,0);
	        	/*
	        	for(int j=0;j <= row_size;j++){
	        		if(i%2 == 0){
	        			changeColorIdInit(i,j);
	        		}
	        	}
	        	*/
	        }
	        resultFlg = false;
		}
		flgCount++;
		}
		
	}

	private void checkSize(){
		if(column_size == 0){
			column_size++;
		}
		if(row_size == 0){
			row_size++;
		}
		color_size+=2;
		/*
		if(color_size == 0){
			color_size+=2;
		}else if(color_size == 1){
			color_size++;
		}
		*/
	}
	
	//random�����ݒ胁�\�b�h
	private void initRandom(){
		setClearFlg();
		
		int col_num = column_size+1;
		int row_num = row_size+1;
		//color_size = color_num-1;
		
		LinearLayout lineCol[];
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(MP, MP);
		lineCol = new LinearLayout[col_num];
		btn     = new Button[col_num][row_num];
		
		mainLay = new LinearLayout(this);
		mainLay.setLayoutParams(lp);
		mainLay.setOrientation(LinearLayout.VERTICAL);
		
		for(int i = 0;i < col_num;i++){
			lineCol[i] = new LinearLayout(this);
			lp.weight = 1;
			lineCol[i].setLayoutParams(lp);
			lineCol[i].setOrientation(LinearLayout.HORIZONTAL);
			
			for(int j=0;j < row_num;j++){
				btn[i][j] = new Button(this);
				btn[i][j].setLayoutParams(lp);
				
				Log.e("colorFlg", "colorFlg:"+colorFlg[i][j]);
				Log.e("size", "column:"+i+":"+"row:"+j+":"+"color:"+color_size);
				
				btn[i][j].setBackgroundResource(colorList[colorFlg[i][j]]);
				//btn[i][j].setBackgroundColor(
						//Color.rgb(colorList[myColor][0], colorList[myColor][1], colorList[myColor][2]));
				btn[i][j].setText(i+","+j);
				btn[i][j].setTextColor(Color.argb(0, 0, 0, 0));
				btn[i][j].setOnClickListener(this);
				lineCol[i].addView(btn[i][j]);
			}
			
			mainLay.addView(lineCol[i]);
			
		}
	}
	
	@Override
	public void onClick(View v) {
		
		changeColorId((Button) v);
		checkResult();
		
		/*
		Button clickBtn = (Button) v;
		String test = clickBtn.getText().toString();
        Log.d("button", ""+test);
        */
	}
	
	//�{�^���������ɏꏊ�ɉ����ď㉺���E��Flg��ύX����
	private void changeColorId(Button clickBtn){
		String clickBtnTxt = clickBtn.getText().toString();
		
		if(!clickBtnTxt.equals(null)){
			String position[] = clickBtnTxt.split(",");
			int this_col = Integer.parseInt(position[0]);
			int this_row = Integer.parseInt(position[1]);
			
			    changeColor(this_col,this_row);
			if(this_col > 0){
				changeColor(this_col-1,this_row);
			}
			if(this_col < column_size){
				changeColor(this_col+1,this_row);
			}
			if(this_row > 0){
				changeColor(this_col,this_row-1);
			}
			if(this_row < row_size){
				changeColor(this_col,this_row+1);
			}
			
			scoreCount++;
		}
	}
	
	//�N���A�p�̃t���O�ƃ{�^���̔w�i��ύX
	private void changeColor(int col_num,int row_num){
		if(colorFlg[col_num][row_num] < color_size-1){
			
			colorFlg[col_num][row_num] += 1;
			int thisColor = colorFlg[col_num][row_num];

			btn[col_num][row_num].setBackgroundResource(colorList[thisColor]);
			//btn[col_num][row_num].setBackgroundColor(
					//Color.rgb(colorList[thisColor][0], colorList[thisColor][1], colorList[thisColor][2]));
		
		}else if(colorFlg[col_num][row_num] == color_size-1){
		
			colorFlg[col_num][row_num] = 0;
			int thisColor = colorFlg[col_num][row_num];
			btn[col_num][row_num].setBackgroundResource(colorList[thisColor]);
			//btn[col_num][row_num].setBackgroundColor(
					//Color.rgb(colorList[thisColor][0], colorList[thisColor][1], colorList[thisColor][2]));
		
		}
	}
	
	//�����ݒ�p�@�N���A���̉�ʂ���[���I�Ƀ{�^�����������ƂŁA�����l���쐬����
	private void changeColorIdInit(int this_col,int this_row){
			
			changeColorFlg(this_col,this_row);
			
			if(this_col > 0){
				changeColorFlg(this_col-1,this_row);
			}
			if(this_col < column_size){
				changeColorFlg(this_col+1,this_row);
			}
			if(this_row > 0){
				changeColorFlg(this_col,this_row-1);
			}
			if(this_row < row_size){
				changeColorFlg(this_col,this_row+1);
			}
			
	}
	
	//�N���A�p�̃t���O�̂ݕύX(�����ݒ�p)
	private void changeColorFlg(int col_num,int row_num){
		if(colorFlg[col_num][row_num] < color_size-1){
			
			colorFlg[col_num][row_num] += 1;
			initColorFlg[col_num][row_num] += 1;
		
		}else if(colorFlg[col_num][row_num] == color_size-1){
		
			colorFlg[col_num][row_num] = 0;
			initColorFlg[col_num][row_num] = 0;
		
		}
	}

	private void checkResult(){
		//int checkFlg0 = 0;
		//int checkFlg1 = 0;
		boolean resultFlg = true;
		int flgCheck = colorFlg[0][0];

		for(int i = 0;i <= column_size;i++){
			for(int j = 0; j <= row_size; j++){
				if(colorFlg[i][j] != flgCheck){
					resultFlg = false;
					break;
				}
			}
		}
		
	    if(resultFlg == true){

	    	Log.e("cleartest","color"+color_size);
	    	
	    	new AlertDialog.Builder(this)
	    	.setTitle("Congratulations!")
	    	.setMessage("Count :" +scoreCount)
	    	.setPositiveButton(
	    			"Close", 
	    			new DialogInterface.OnClickListener() {
	    				@Override
	    				public void onClick(DialogInterface dialog, int which) {  
	    				}
	    			})
	    			.show();

	    	scoreCount = 0;
        
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		//getMenuInflater().inflate(R.menu.main, menu);

		MenuItem target = menu.add(0,MENU_SELECT_RESET,0,"Reset");
		target.setIcon(android.R.drawable.ic_menu_rotate);		
		target.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM  );
		
		/*
		target = menu.add(0,MENU_SELECT_RANDOM_RESET,1,"Reset Random");
		target.setIcon(android.R.drawable.ic_menu_rotate);		
		target.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM  );
		*/
		//return super.onCreateOptionsMenu(menu);
		return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	    case MENU_SELECT_RESET:

	        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
	        // �A���[�g�_�C�A���O�̃^�C�g����ݒ肵�܂�
	        alertDialogBuilder.setTitle("Reset Menu");
	        // �A���[�g�_�C�A���O�̃��b�Z�[�W��ݒ肵�܂�
	        alertDialogBuilder.setMessage("��蒼���܂����H");
	        // �A���[�g�_�C�A���O�̍m��{�^�����N���b�N���ꂽ���ɌĂяo�����R�[���o�b�N���X�i�[��o�^���܂�
	        alertDialogBuilder.setPositiveButton("�~",
	                new DialogInterface.OnClickListener() {
	                    @Override
	                    public void onClick(DialogInterface dialog, int which) {
	                    }
	                });
	        // �A���[�g�_�C�A���O�̒����{�^�����N���b�N���ꂽ���ɌĂяo�����R�[���o�b�N���X�i�[��o�^���܂�
	        alertDialogBuilder.setNeutralButton("Random",
	                new DialogInterface.OnClickListener() {
	                    @Override
	                    public void onClick(DialogInterface dialog, int which) {
	                    	scoreCount = 0;
	                    	initRandom();
	                    	setContentView(mainLay);
	                    }
	                });
	        // �A���[�g�_�C�A���O�̔ے�{�^�����N���b�N���ꂽ���ɌĂяo�����R�[���o�b�N���X�i�[��o�^���܂�
	        alertDialogBuilder.setNegativeButton("Reset",
	                new DialogInterface.OnClickListener() {
	                    @Override
	                    public void onClick(DialogInterface dialog, int which) {
	                    	scoreCount = 0;
	                    	initReset(column_size+1,row_size+1,color_size+1);
	            	        setContentView(mainLay);
	                    }
	                });
	        // �A���[�g�_�C�A���O�̃L�����Z�����\���ǂ�����ݒ肵�܂�
	        alertDialogBuilder.setCancelable(true);
	        AlertDialog alertDialog = alertDialogBuilder.create();
	        // �A���[�g�_�C�A���O��\�����܂�
	        alertDialog.show();

	        return true;
	 
	    }
	    return false;
	}
	
}
