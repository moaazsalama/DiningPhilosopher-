/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.mycompany.dine;

/**
 *
 * @author Moaaz Salama
 */
public class Dine{
	public static void main(String[] args){

		int rounds=10;

		Log.msg(String.valueOf(rounds));
 
		Chopstick[] chopistics = new Chopstick[5];

		//initlize the chopistics
		for(int i=0; i< chopistics.length; i++){
			chopistics[i] = new Chopstick("C: "+i);
		}
		Philosopher[] philosophers = new Philosopher[5];
		//for(i=0; i<philosophers.length; i++){
		philosophers[0] = new Philosopher("P: 0 - ", chopistics[0], chopistics[1], rounds);
		philosophers[1] = new Philosopher("P: 1 - ", chopistics[1], chopistics[2], rounds);
		philosophers[2] = new Philosopher("P: 2 - ", chopistics[2], chopistics[3], rounds);
		philosophers[3] = new Philosopher("P: 3 - ", chopistics[3], chopistics[4], rounds);
		philosophers[4] = new Philosopher("P: 4 - ", chopistics[0], chopistics[4], rounds);
               
           
                           java.awt.EventQueue.invokeLater(() -> {
                               new GUI(philosophers).setVisible(true);
                });
		
 	}
}

// State : 2 = Eat, 1 = think
class Philosopher extends Thread
{
	private final Chopstick _leftChopistick;
	private final Chopstick _rightChopistick;
        public Boolean eating=false;
        public Boolean thinking=false;
	private final String _name;
	private final int _rounds;

	public Philosopher ( String name, Chopstick _left, Chopstick _right, int rounds){
		this._name = name;
		_leftChopistick = _left;
		_rightChopistick = _right;
		_rounds = rounds;
	}
 
	public synchronized void  eat()
	 {
		if(! _leftChopistick.used){
			if(!_rightChopistick.used){
				_leftChopistick.take();
				_rightChopistick.take();
                                
				Log.msg(_name + " : Eat");
				eating=true;
				Log.Delay(1000);

				_rightChopistick.release();
		 		_leftChopistick.release();
                                eating=false;
			}
		}		
		think();
                thinking=true;
	}

	public synchronized void think(){
		 	Log.msg(_name + " : Think");
		 	Log.Delay(1000);
		
	}

	public void run(){
		for(int i=0; i<=_rounds; i++){
			eat();
		}
	}
}

class Log{

	public static void msg(String msg){
		System.out.println(msg);
	}
	public static void Delay(int ms){
		try{
			Thread.sleep(ms);
		}
		catch(InterruptedException ex){ }
	}
}

class Chopstick{

	public boolean used;
	public String _name;

	public Chopstick(String _name){
		this._name = _name;
	}

	public synchronized void take() {
		Log.msg ("Used :: " + _name );
		this.used = true;
	}
	public synchronized void release() {
		Log.msg ("Released :: " + _name );
		this.used = false ;
	}
}
