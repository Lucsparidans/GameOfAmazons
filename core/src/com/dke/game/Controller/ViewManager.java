package com.dke.game.Controller;

import com.dke.game.Views.View;
import java.util.Stack;

public class ViewManager {
    private static Stack<View> viewStack = new Stack<>();
    public ViewManager pop(){
        viewStack.peek().dispose();
        viewStack.pop();
        return this;
    }
    public void push(View view){
        view.create();
        viewStack.push(view);
    }

    public View peek(){
        return viewStack.peek();
    }
}
