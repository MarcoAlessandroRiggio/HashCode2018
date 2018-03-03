package entity;

public class DebugEx {

    private static int callsToCanRideBeMadeInTime = 0;

    public static void increaseCallsToCanRideBeMadeInTime() {
        callsToCanRideBeMadeInTime++;
        if(callsToCanRideBeMadeInTime % 500 == 0){
            System.out.println("#CallsToCanRideBeMadeInTime = "+callsToCanRideBeMadeInTime);
        }
    }
}
