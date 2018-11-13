package com.dke.game.Controller;

import com.dke.game.Views.View;
import java.util.Stack;

/**
 * This class represents a managed stack of views which can be used to render specific states
 */
public class ViewManager {
    private static Stack<View> viewStack = new Stack<View>();
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
