package com.slb.ttdandroidframework.command;


import com.github.pires.obd.exceptions.BusInitException;
import com.github.pires.obd.exceptions.MisunderstoodCommandException;
import com.github.pires.obd.exceptions.NoDataException;
import com.github.pires.obd.exceptions.NonNumericResponseException;
import com.github.pires.obd.exceptions.ResponseException;
import com.github.pires.obd.exceptions.StoppedException;
import com.github.pires.obd.exceptions.UnableToConnectException;
import com.github.pires.obd.exceptions.UnknownErrorException;
import com.github.pires.obd.exceptions.UnsupportedCommandException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.regex.Pattern;

public abstract class ObdCommand {
    private final Class[] ERROR_CLASSES;
    protected ArrayList<Integer> buffer;
    protected String cmd;
    protected boolean useImperialUnits;
    protected String rawData;
    protected Long responseDelayInMs;
    private long start;
    private long end;
    private static Pattern WHITESPACE_PATTERN = Pattern.compile("\\s");
    private static Pattern BUSINIT_PATTERN = Pattern.compile("(BUS INIT)|(BUSINIT)|(\\.)");
    private static Pattern SEARCHING_PATTERN = Pattern.compile("SEARCHING");
    private static Pattern DIGITS_LETTERS_PATTERN = Pattern.compile("([0-9A-F])+");

    public ObdCommand(String command) {
        this.ERROR_CLASSES = new Class[]{UnableToConnectException.class, BusInitException.class, MisunderstoodCommandException.class, NoDataException.class, StoppedException.class, UnknownErrorException.class, UnsupportedCommandException.class};
        this.buffer = null;
        this.cmd = null;
        this.useImperialUnits = false;
        this.rawData = null;
        this.responseDelayInMs = null;
        this.cmd = command;
        this.buffer = new ArrayList();
    }

    private ObdCommand() {
        this.ERROR_CLASSES = new Class[]{UnableToConnectException.class, BusInitException.class, MisunderstoodCommandException.class, NoDataException.class, StoppedException.class, UnknownErrorException.class, UnsupportedCommandException.class};
        this.buffer = null;
        this.cmd = null;
        this.useImperialUnits = false;
        this.rawData = null;
        this.responseDelayInMs = null;
    }

    public ObdCommand(ObdCommand other) {
        this(other.cmd);
    }

    public void run(InputStream in, OutputStream out) throws IOException, InterruptedException {
        Class var3 = com.github.pires.obd.commands.ObdCommand.class;
        synchronized(com.github.pires.obd.commands.ObdCommand.class) {
            this.start = System.currentTimeMillis();
            this.sendCommand(out);
            this.readResult(in);
            this.end = System.currentTimeMillis();
        }
    }

    protected void sendCommand(OutputStream out) throws IOException, InterruptedException {
        out.write((this.cmd + "\r").getBytes());
        System.out.println("################ send command : " + this.cmd + " ##############################");
        out.flush();
        if (this.responseDelayInMs != null && this.responseDelayInMs > 0L) {
            Thread.sleep(this.responseDelayInMs);
        }

    }

    protected void resendCommand(OutputStream out) throws IOException, InterruptedException {
        out.write("\r".getBytes());
        out.flush();
        if (this.responseDelayInMs != null && this.responseDelayInMs > 0L) {
            Thread.sleep(this.responseDelayInMs);
        }

    }

    protected void readResult(InputStream in) throws IOException {
        this.readRawData(in);
        this.checkForErrors();
        this.fillBuffer();
        this.performCalculations();
    }

    protected abstract void performCalculations();

    protected String replaceAll(Pattern pattern, String input, String replacement) {
        return pattern.matcher(input).replaceAll(replacement);
    }

    protected String removeAll(Pattern pattern, String input) {
        return pattern.matcher(input).replaceAll("");
    }

    protected void fillBuffer() {
        this.rawData = this.removeAll(WHITESPACE_PATTERN, this.rawData);
        this.rawData = this.removeAll(BUSINIT_PATTERN, this.rawData);
//        if (!DIGITS_LETTERS_PATTERN.matcher(this.rawData).matches()) {
//            throw new NonNumericResponseException(this.rawData);
//        } else {
//            this.buffer.clear();
//            int begin = 0;
//
//            for(int end = 2; end <= this.rawData.length(); end += 2) {
//                this.buffer.add(Integer.decode("0x" + this.rawData.substring(begin, end)));
//                begin = end;
//            }
//
//        }
    }

    protected void readRawData(InputStream in) throws IOException {
        StringBuilder res = new StringBuilder();

        byte b;
        while((b = (byte)in.read()) > -1) {
            char c = (char)b;
            if (c == '>') {
                break;
            }

            res.append(c);
        }

        this.rawData = this.removeAll(SEARCHING_PATTERN, res.toString());
        this.rawData = this.removeAll(WHITESPACE_PATTERN, this.rawData);
        System.out.println("################ formated read rawData : " + this.rawData + " ##############################");
    }

    void checkForErrors() {
        Class[] var1 = this.ERROR_CLASSES;
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            Class errorClass = var1[var3];

            ResponseException messageError;
            try {
                messageError = (ResponseException)errorClass.newInstance();
                messageError.setCommand(this.cmd);
            } catch (InstantiationException var7) {
                throw new RuntimeException(var7);
            } catch (IllegalAccessException var8) {
                throw new RuntimeException(var8);
            }

            if (messageError.isError(this.rawData)) {
                throw messageError;
            }
        }

    }

    public String getResult() {
        return this.rawData;
    }

    public abstract String getFormattedResult();

    public abstract String getCalculatedResult();

    protected ArrayList<Integer> getBuffer() {
        return this.buffer;
    }

    public boolean useImperialUnits() {
        return this.useImperialUnits;
    }

    public String getResultUnit() {
        return "";
    }

    public void useImperialUnits(boolean isImperial) {
        this.useImperialUnits = isImperial;
    }

    public abstract String getName();

    public Long getResponseTimeDelay() {
        return this.responseDelayInMs;
    }

    public void setResponseTimeDelay(Long responseDelayInMs) {
        this.responseDelayInMs = responseDelayInMs;
    }

    public long getStart() {
        return this.start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getEnd() {
        return this.end;
    }

    public void setEnd(long end) {
        this.end = end;
    }

    public final String getCommandPID() {
        return this.cmd.substring(3);
    }

    public final String getCommandMode() {
        return this.cmd.length() >= 2 ? this.cmd.substring(0, 2) : this.cmd;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            ObdCommand that = (ObdCommand)o;
            return this.cmd != null ? this.cmd.equals(that.cmd) : that.cmd == null;
        } else {
            return false;
        }
    }

    public int hashCode() {
        return this.cmd != null ? this.cmd.hashCode() : 0;
    }
}
