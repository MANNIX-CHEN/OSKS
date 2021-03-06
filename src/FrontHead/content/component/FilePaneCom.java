package FrontHead.content.component;

import BackGround.Server;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import sample.Controller;

import java.util.Timer;
import java.util.TimerTask;


public abstract class FilePaneCom extends StackPane {
    protected Button img;   //显示图标
    protected Label title;  //显示名称
    protected int clickNum;
    protected int callTimes;
    protected Controller controller;
    public boolean isClickedflag; //用于判断是否选中


    public abstract void setImg();

    public abstract void enter() throws Exception;


    Server server;

    public FilePaneCom(String name, Controller controller) {
        img = new Button();
        img.getStyleClass().add("test_white_back");
        title = new Label();
        setController(controller);
        setServer(controller.getServer());

        //设置自己的样式
        getStyleClass().add("filePaneComPane");
        //设置图片
        setImg();
        setAlignment(Pos.CENTER);

        //设置文本
        title.setText(name);

        title.getStyleClass().add("filePaneComTitle");

        getChildren().add(img);
        getChildren().add(title);

        int x;
        clickNum = 0;
        callTimes = 0;
        initMouseEvent();
    }

    protected  void initMouseEvent(){
        img.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent event) -> {
            clickEvent();
            controller.updatePaneStyle(event);
        });
        addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent event) -> {
            clickEvent();
        });

        //移入
        addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent event) -> {
            if(controller.getCurClickedCom() != this) {
                turnBlue();
            }
        });

        //移出
        addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent event) -> {
            if (controller.getCurClickedCom() != this) {
                turnWhite();
            }
        });
    }

    private void clickEvent(){
        if(controller.getCurClickedCom()!=null &&
                controller.getCurClickedCom()!=this)
            controller.getCurClickedCom().turnWhite();//变化clickedCom
        isClickedflag = true; //用于判断已经选中的单位不会再有移入移出的变化
        turnDeepBlue();
        controller.setCurClickedCom(this);

        clickNum ++;
        Timer timer = new Timer();
        if (clickNum == 2) {
            //System.out.println("click twice");
            try {
                enter();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                //双击事件
                clickNum = 0;
                this.cancel();
                return;
            }
        }, 500, 500);

    }
    public void turnWhite(){
        getStyleClass().removeAll("test_blue_back","test_deepblue_back");
        img.getStyleClass().removeAll("test_blue_back","test_deepblue_back");
        getStyleClass().add("test_white_back");
        img.getStyleClass().add("test_white_back");
    }

    public void turnBlue(){
        getStyleClass().removeAll("test_white_back","test_deepblue_back");
        img.getStyleClass().removeAll("test_white_back","test_deepblue_back");
        getStyleClass().add("test_blue_back");
        img.getStyleClass().add("test_blue_back");
    }
    public void turnDeepBlue(){
        getStyleClass().removeAll("test_white_back","test_blue_back");
        img.getStyleClass().removeAll("test_white_back","test_blue_back");
        getStyleClass().add("test_deepblue_back");
        img.getStyleClass().add("test_deepblue_back");
    }
    public Controller getController() {
        return controller;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void setServer(Server server) {
        this.server = server;
    }
}