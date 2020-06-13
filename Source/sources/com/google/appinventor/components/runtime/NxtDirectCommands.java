package com.google.appinventor.components.runtime;

import android.util.Log;
import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.UsesPermissions;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.runtime.util.ErrorMessages;
import com.google.appinventor.components.runtime.util.Ev3Constants.Opcode;
import com.google.appinventor.components.runtime.util.YailList;
import gnu.kawa.servlet.HttpRequestContext;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SimpleObject
@DesignerComponent(category = ComponentCategory.LEGOMINDSTORMS, description = "A component that provides a low-level interface to a LEGO MINDSTORMS NXT robot, with functions to send NXT Direct Commands.", iconName = "images/legoMindstormsNxt.png", nonVisible = true, version = 1)
@UsesPermissions(permissionNames = "android.permission.INTERNET,android.permission.WRITE_EXTERNAL_STORAGE,android.permission.READ_EXTERNAL_STORAGE")
public class NxtDirectCommands extends LegoMindstormsNxtBase {
    public NxtDirectCommands(ComponentContainer container) {
        super(container, "NxtDirectCommands");
    }

    @SimpleFunction(description = "Start execution of a previously downloaded program on the robot.")
    public void StartProgram(String programName) {
        String functionName = "StartProgram";
        if (checkBluetooth(functionName)) {
            if (programName.length() == 0) {
                this.form.dispatchErrorOccurredEvent(this, functionName, ErrorMessages.ERROR_NXT_INVALID_PROGRAM_NAME, new Object[0]);
                return;
            }
            if (programName.indexOf(".") == -1) {
                programName = programName + ".rxe";
            }
            byte[] command = new byte[22];
            command[0] = Byte.MIN_VALUE;
            command[1] = 0;
            copyStringValueToBytes(programName, command, 2, 19);
            sendCommand(functionName, command);
        }
    }

    @SimpleFunction(description = "Stop execution of the currently running program on the robot.")
    public void StopProgram() {
        String functionName = "StopProgram";
        if (checkBluetooth(functionName)) {
            sendCommand(functionName, new byte[]{Byte.MIN_VALUE, 1});
        }
    }

    @SimpleFunction(description = "Play a sound file on the robot.")
    public void PlaySoundFile(String fileName) {
        String functionName = "PlaySoundFile";
        if (checkBluetooth(functionName)) {
            if (fileName.length() == 0) {
                this.form.dispatchErrorOccurredEvent(this, functionName, ErrorMessages.ERROR_NXT_INVALID_FILE_NAME, new Object[0]);
                return;
            }
            if (fileName.indexOf(".") == -1) {
                fileName = fileName + ".rso";
            }
            byte[] command = new byte[23];
            command[0] = Byte.MIN_VALUE;
            command[1] = 2;
            copyBooleanValueToBytes(false, command, 2);
            copyStringValueToBytes(fileName, command, 3, 19);
            sendCommand(functionName, command);
        }
    }

    @SimpleFunction(description = "Make the robot play a tone.")
    public void PlayTone(int frequencyHz, int durationMs) {
        String functionName = "PlayTone";
        if (checkBluetooth(functionName)) {
            if (frequencyHz < 200) {
                Log.w(this.logTag, "frequencyHz " + frequencyHz + " is invalid, using 200.");
                frequencyHz = HttpRequestContext.HTTP_OK;
            }
            if (frequencyHz > 14000) {
                Log.w(this.logTag, "frequencyHz " + frequencyHz + " is invalid, using 14000.");
                frequencyHz = 14000;
            }
            byte[] command = new byte[6];
            command[0] = Byte.MIN_VALUE;
            command[1] = 3;
            copyUWORDValueToBytes(frequencyHz, command, 2);
            copyUWORDValueToBytes(durationMs, command, 4);
            sendCommand(functionName, command);
        }
    }

    @SimpleFunction(description = "Sets the output state of a motor on the robot.")
    public void SetOutputState(String motorPortLetter, int power, int mode, int regulationMode, int turnRatio, int runState, long tachoLimit) {
        String functionName = "SetOutputState";
        if (checkBluetooth(functionName)) {
            try {
                setOutputState(functionName, convertMotorPortLetterToNumber(motorPortLetter), power, mode, regulationMode, sanitizeTurnRatio(turnRatio), runState, tachoLimit);
            } catch (IllegalArgumentException e) {
                this.form.dispatchErrorOccurredEvent(this, functionName, ErrorMessages.ERROR_NXT_INVALID_MOTOR_PORT, motorPortLetter);
            }
        }
    }

    @SimpleFunction(description = "Configure an input sensor on the robot.")
    public void SetInputMode(String sensorPortLetter, int sensorType, int sensorMode) {
        String functionName = "SetInputMode";
        if (checkBluetooth(functionName)) {
            try {
                setInputMode(functionName, convertSensorPortLetterToNumber(sensorPortLetter), sensorType, sensorMode);
            } catch (IllegalArgumentException e) {
                this.form.dispatchErrorOccurredEvent(this, functionName, ErrorMessages.ERROR_NXT_INVALID_SENSOR_PORT, sensorPortLetter);
            }
        }
    }

    @SimpleFunction(description = "Reads the output state of a motor on the robot.")
    public List<Number> GetOutputState(String motorPortLetter) {
        String functionName = "GetOutputState";
        if (!checkBluetooth(functionName)) {
            return new ArrayList();
        }
        try {
            byte[] returnPackage = getOutputState(functionName, convertMotorPortLetterToNumber(motorPortLetter));
            if (returnPackage == null) {
                return new ArrayList();
            }
            List<Number> outputState = new ArrayList<>();
            outputState.add(Integer.valueOf(getSBYTEValueFromBytes(returnPackage, 4)));
            outputState.add(Integer.valueOf(getUBYTEValueFromBytes(returnPackage, 5)));
            outputState.add(Integer.valueOf(getUBYTEValueFromBytes(returnPackage, 6)));
            outputState.add(Integer.valueOf(getSBYTEValueFromBytes(returnPackage, 7)));
            outputState.add(Integer.valueOf(getUBYTEValueFromBytes(returnPackage, 8)));
            outputState.add(Long.valueOf(getULONGValueFromBytes(returnPackage, 9)));
            outputState.add(Integer.valueOf(getSLONGValueFromBytes(returnPackage, 13)));
            outputState.add(Integer.valueOf(getSLONGValueFromBytes(returnPackage, 17)));
            outputState.add(Integer.valueOf(getSLONGValueFromBytes(returnPackage, 21)));
            return outputState;
        } catch (IllegalArgumentException e) {
            this.form.dispatchErrorOccurredEvent(this, functionName, ErrorMessages.ERROR_NXT_INVALID_MOTOR_PORT, motorPortLetter);
            return new ArrayList();
        }
    }

    private byte[] getOutputState(String functionName, int port) {
        byte[] command = new byte[3];
        command[0] = 0;
        command[1] = 6;
        copyUBYTEValueToBytes(port, command, 2);
        byte[] returnPackage = sendCommandAndReceiveReturnPackage(functionName, command);
        if (evaluateStatus(functionName, returnPackage, command[1])) {
            if (returnPackage.length == 25) {
                return returnPackage;
            }
            Log.w(this.logTag, functionName + ": unexpected return package length " + returnPackage.length + " (expected 25)");
        }
        return null;
    }

    @SimpleFunction(description = "Reads the values of an input sensor on the robot. Assumes sensor type has been configured via SetInputMode.")
    public List<Object> GetInputValues(String sensorPortLetter) {
        String functionName = "GetInputValues";
        if (!checkBluetooth(functionName)) {
            return new ArrayList();
        }
        try {
            byte[] returnPackage = getInputValues(functionName, convertSensorPortLetterToNumber(sensorPortLetter));
            if (returnPackage == null) {
                return new ArrayList();
            }
            List<Object> inputValues = new ArrayList<>();
            inputValues.add(Boolean.valueOf(getBooleanValueFromBytes(returnPackage, 4)));
            inputValues.add(Boolean.valueOf(getBooleanValueFromBytes(returnPackage, 5)));
            inputValues.add(Integer.valueOf(getUBYTEValueFromBytes(returnPackage, 6)));
            inputValues.add(Integer.valueOf(getUBYTEValueFromBytes(returnPackage, 7)));
            inputValues.add(Integer.valueOf(getUWORDValueFromBytes(returnPackage, 8)));
            inputValues.add(Integer.valueOf(getUWORDValueFromBytes(returnPackage, 10)));
            inputValues.add(Integer.valueOf(getSWORDValueFromBytes(returnPackage, 12)));
            inputValues.add(Integer.valueOf(getSWORDValueFromBytes(returnPackage, 14)));
            return inputValues;
        } catch (IllegalArgumentException e) {
            this.form.dispatchErrorOccurredEvent(this, functionName, ErrorMessages.ERROR_NXT_INVALID_SENSOR_PORT, sensorPortLetter);
            return new ArrayList();
        }
    }

    @SimpleFunction(description = "Reset the scaled value of an input sensor on the robot.")
    public void ResetInputScaledValue(String sensorPortLetter) {
        String functionName = "ResetInputScaledValue";
        if (checkBluetooth(functionName)) {
            try {
                int port = convertSensorPortLetterToNumber(sensorPortLetter);
                resetInputScaledValue(functionName, port);
                byte[] command = new byte[3];
                command[0] = Byte.MIN_VALUE;
                command[1] = 8;
                copyUBYTEValueToBytes(port, command, 2);
                sendCommand(functionName, command);
            } catch (IllegalArgumentException e) {
                this.form.dispatchErrorOccurredEvent(this, functionName, ErrorMessages.ERROR_NXT_INVALID_SENSOR_PORT, sensorPortLetter);
            }
        }
    }

    @SimpleFunction(description = "Write a message to a mailbox (1-10) on the robot.")
    public void MessageWrite(int mailbox, String message) {
        String functionName = "MessageWrite";
        if (checkBluetooth(functionName)) {
            if (mailbox < 1 || mailbox > 10) {
                this.form.dispatchErrorOccurredEvent(this, functionName, ErrorMessages.ERROR_NXT_INVALID_MAILBOX, Integer.valueOf(mailbox));
                return;
            }
            int messageLength = message.length();
            if (messageLength > 58) {
                this.form.dispatchErrorOccurredEvent(this, functionName, ErrorMessages.ERROR_NXT_MESSAGE_TOO_LONG, new Object[0]);
                return;
            }
            int mailbox2 = mailbox - 1;
            byte[] command = new byte[(messageLength + 4 + 1)];
            command[0] = Byte.MIN_VALUE;
            command[1] = 9;
            copyUBYTEValueToBytes(mailbox2, command, 2);
            copyUBYTEValueToBytes(messageLength + 1, command, 3);
            copyStringValueToBytes(message, command, 4, messageLength);
            sendCommand(functionName, command);
        }
    }

    @SimpleFunction(description = "Reset motor position.")
    public void ResetMotorPosition(String motorPortLetter, boolean relative) {
        String functionName = "ResetMotorPosition";
        if (checkBluetooth(functionName)) {
            try {
                int port = convertMotorPortLetterToNumber(motorPortLetter);
                byte[] command = new byte[4];
                command[0] = Byte.MIN_VALUE;
                command[1] = 10;
                copyUBYTEValueToBytes(port, command, 2);
                copyBooleanValueToBytes(relative, command, 3);
                sendCommand(functionName, command);
            } catch (IllegalArgumentException e) {
                this.form.dispatchErrorOccurredEvent(this, functionName, ErrorMessages.ERROR_NXT_INVALID_MOTOR_PORT, motorPortLetter);
            }
        }
    }

    @SimpleFunction(description = "Get the battery level for the robot. Returns the voltage in millivolts.")
    public int GetBatteryLevel() {
        String functionName = "GetBatteryLevel";
        if (!checkBluetooth(functionName)) {
            return 0;
        }
        byte[] command = {0, 11};
        byte[] returnPackage = sendCommandAndReceiveReturnPackage(functionName, command);
        if (!evaluateStatus(functionName, returnPackage, command[1])) {
            return 0;
        }
        if (returnPackage.length == 5) {
            return getUWORDValueFromBytes(returnPackage, 3);
        }
        Log.w(this.logTag, "GetBatteryLevel: unexpected return package length " + returnPackage.length + " (expected 5)");
        return 0;
    }

    @SimpleFunction(description = "Stop sound playback.")
    public void StopSoundPlayback() {
        String functionName = "StopSoundPlayback";
        if (checkBluetooth(functionName)) {
            sendCommand(functionName, new byte[]{Byte.MIN_VALUE, 12});
        }
    }

    @SimpleFunction(description = "Keep Alive. Returns the current sleep time limit in milliseconds.")
    public long KeepAlive() {
        String functionName = "KeepAlive";
        if (!checkBluetooth(functionName)) {
            return 0;
        }
        byte[] command = {0, 13};
        byte[] returnPackage = sendCommandAndReceiveReturnPackage(functionName, command);
        if (!evaluateStatus(functionName, returnPackage, command[1])) {
            return 0;
        }
        if (returnPackage.length == 7) {
            return getULONGValueFromBytes(returnPackage, 3);
        }
        Log.w(this.logTag, "KeepAlive: unexpected return package length " + returnPackage.length + " (expected 7)");
        return 0;
    }

    @SimpleFunction(description = "Returns the count of available bytes to read.")
    public int LsGetStatus(String sensorPortLetter) {
        String functionName = "LsGetStatus";
        if (!checkBluetooth(functionName)) {
            return 0;
        }
        try {
            return lsGetStatus(functionName, convertSensorPortLetterToNumber(sensorPortLetter));
        } catch (IllegalArgumentException e) {
            this.form.dispatchErrorOccurredEvent(this, functionName, ErrorMessages.ERROR_NXT_INVALID_SENSOR_PORT, sensorPortLetter);
            return 0;
        }
    }

    @SimpleFunction(description = "Writes low speed data to an input sensor on the robot. Assumes sensor type has been configured via SetInputMode.")
    public void LsWrite(String sensorPortLetter, YailList list, int rxDataLength) {
        String functionName = "LsWrite";
        if (checkBluetooth(functionName)) {
            try {
                int port = convertSensorPortLetterToNumber(sensorPortLetter);
                if (list.size() > 16) {
                    this.form.dispatchErrorOccurredEvent(this, functionName, ErrorMessages.ERROR_NXT_DATA_TOO_LARGE, new Object[0]);
                    return;
                }
                Object[] array = list.toArray();
                byte[] bytes = new byte[array.length];
                int i = 0;
                while (i < array.length) {
                    try {
                        int n = Integer.decode(array[i].toString()).intValue();
                        bytes[i] = (byte) (n & 255);
                        int n2 = n >> 8;
                        if (n2 == 0 || n2 == -1) {
                            i++;
                        } else {
                            this.form.dispatchErrorOccurredEvent(this, functionName, ErrorMessages.ERROR_NXT_COULD_NOT_FIT_ELEMENT_IN_BYTE, Integer.valueOf(i + 1));
                            return;
                        }
                    } catch (NumberFormatException e) {
                        this.form.dispatchErrorOccurredEvent(this, functionName, ErrorMessages.ERROR_NXT_COULD_NOT_DECODE_ELEMENT, Integer.valueOf(i + 1));
                        return;
                    }
                }
                lsWrite(functionName, port, bytes, rxDataLength);
            } catch (IllegalArgumentException e2) {
                this.form.dispatchErrorOccurredEvent(this, functionName, ErrorMessages.ERROR_NXT_INVALID_SENSOR_PORT, sensorPortLetter);
            }
        }
    }

    @SimpleFunction(description = "Reads unsigned low speed data from an input sensor on the robot. Assumes sensor type has been configured via SetInputMode.")
    public List<Integer> LsRead(String sensorPortLetter) {
        String functionName = "LsRead";
        if (!checkBluetooth(functionName)) {
            return new ArrayList();
        }
        try {
            byte[] returnPackage = lsRead(functionName, convertSensorPortLetterToNumber(sensorPortLetter));
            if (returnPackage == null) {
                return new ArrayList();
            }
            List<Integer> list = new ArrayList<>();
            int count = getUBYTEValueFromBytes(returnPackage, 3);
            for (int i = 0; i < count; i++) {
                list.add(Integer.valueOf(returnPackage[i + 4] & 255));
            }
            return list;
        } catch (IllegalArgumentException e) {
            this.form.dispatchErrorOccurredEvent(this, functionName, ErrorMessages.ERROR_NXT_INVALID_SENSOR_PORT, sensorPortLetter);
            return new ArrayList();
        }
    }

    @SimpleFunction(description = "Get the name of currently running program on the robot.")
    public String GetCurrentProgramName() {
        String functionName = "GetCurrentProgramName";
        if (!checkBluetooth(functionName)) {
            return "";
        }
        byte[] command = {0, 17};
        byte[] returnPackage = sendCommandAndReceiveReturnPackage(functionName, command);
        int status = getStatus(functionName, returnPackage, command[1]);
        if (status == 0) {
            return getStringValueFromBytes(returnPackage, 3);
        }
        if (status == 236) {
            return "";
        }
        evaluateStatus(functionName, returnPackage, command[1]);
        return "";
    }

    @SimpleFunction(description = "Read a message from a mailbox (1-10) on the robot.")
    public String MessageRead(int mailbox) {
        String functionName = "MessageRead";
        if (!checkBluetooth(functionName)) {
            return "";
        }
        if (mailbox < 1 || mailbox > 10) {
            this.form.dispatchErrorOccurredEvent(this, functionName, ErrorMessages.ERROR_NXT_INVALID_MAILBOX, Integer.valueOf(mailbox));
            return "";
        }
        int mailbox2 = mailbox - 1;
        byte[] command = new byte[5];
        command[0] = 0;
        command[1] = 19;
        copyUBYTEValueToBytes(0, command, 2);
        copyUBYTEValueToBytes(mailbox2, command, 3);
        copyBooleanValueToBytes(true, command, 4);
        byte[] returnPackage = sendCommandAndReceiveReturnPackage(functionName, command);
        if (evaluateStatus(functionName, returnPackage, command[1])) {
            if (returnPackage.length == 64) {
                int mailboxEcho = getUBYTEValueFromBytes(returnPackage, 3);
                if (mailboxEcho != mailbox2) {
                    Log.w(this.logTag, "MessageRead: unexpected return mailbox: " + mailboxEcho + " (expected " + mailbox2 + ")");
                }
                return getStringValueFromBytes(returnPackage, 5, getUBYTEValueFromBytes(returnPackage, 4) - 1);
            }
            Log.w(this.logTag, "MessageRead: unexpected return package length " + returnPackage.length + " (expected 64)");
        }
        return "";
    }

    /* JADX WARNING: Removed duplicated region for block: B:20:0x007a A[SYNTHETIC, Splitter:B:20:0x007a] */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x00aa  */
    @com.google.appinventor.components.annotations.SimpleFunction(description = "Download a file to the robot.")
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void DownloadFile(java.lang.String r20, java.lang.String r21) {
        /*
            r19 = this;
            java.lang.String r5 = "DownloadFile"
            r0 = r19
            boolean r14 = r0.checkBluetooth(r5)
            if (r14 != 0) goto L_0x000b
        L_0x000a:
            return
        L_0x000b:
            int r14 = r20.length()
            if (r14 != 0) goto L_0x0027
            r0 = r19
            com.google.appinventor.components.runtime.Form r14 = r0.form
            r15 = 414(0x19e, float:5.8E-43)
            r16 = 0
            r0 = r16
            java.lang.Object[] r0 = new java.lang.Object[r0]
            r16 = r0
            r0 = r19
            r1 = r16
            r14.dispatchErrorOccurredEvent(r0, r5, r15, r1)
            goto L_0x000a
        L_0x0027:
            int r14 = r21.length()
            if (r14 != 0) goto L_0x0043
            r0 = r19
            com.google.appinventor.components.runtime.Form r14 = r0.form
            r15 = 415(0x19f, float:5.82E-43)
            r16 = 0
            r0 = r16
            java.lang.Object[] r0 = new java.lang.Object[r0]
            r16 = r0
            r0 = r19
            r1 = r16
            r14.dispatchErrorOccurredEvent(r0, r5, r15, r1)
            goto L_0x000a
        L_0x0043:
            r0 = r19
            com.google.appinventor.components.runtime.Form r14 = r0.form     // Catch:{ IOException -> 0x0081 }
            r0 = r20
            java.io.File r12 = com.google.appinventor.components.runtime.util.MediaUtil.copyMediaToTempFile(r14, r0)     // Catch:{ IOException -> 0x0081 }
            java.io.BufferedInputStream r9 = new java.io.BufferedInputStream     // Catch:{ all -> 0x00ef }
            java.io.FileInputStream r14 = com.google.appinventor.components.runtime.util.FileUtil.openFile(r12)     // Catch:{ all -> 0x00ef }
            r15 = 1024(0x400, float:1.435E-42)
            r9.<init>(r14, r15)     // Catch:{ all -> 0x00ef }
            long r6 = r12.length()     // Catch:{ all -> 0x00ea }
            java.lang.String r14 = ".rxe"
            r0 = r21
            boolean r14 = r0.endsWith(r14)     // Catch:{ all -> 0x00ea }
            if (r14 != 0) goto L_0x0070
            java.lang.String r14 = ".ric"
            r0 = r21
            boolean r14 = r0.endsWith(r14)     // Catch:{ all -> 0x00ea }
            if (r14 == 0) goto L_0x00a1
        L_0x0070:
            r0 = r19
            r1 = r21
            java.lang.Integer r8 = r0.openWriteLinear(r5, r1, r6)     // Catch:{ all -> 0x00ea }
        L_0x0078:
            if (r8 != 0) goto L_0x00aa
            r9.close()     // Catch:{ all -> 0x00ef }
            r12.delete()     // Catch:{ IOException -> 0x0081 }
            goto L_0x000a
        L_0x0081:
            r4 = move-exception
            r0 = r19
            com.google.appinventor.components.runtime.Form r14 = r0.form
            r15 = 416(0x1a0, float:5.83E-43)
            r16 = 1
            r0 = r16
            java.lang.Object[] r0 = new java.lang.Object[r0]
            r16 = r0
            r17 = 0
            java.lang.String r18 = r4.getMessage()
            r16[r17] = r18
            r0 = r19
            r1 = r16
            r14.dispatchErrorOccurredEvent(r0, r5, r15, r1)
            goto L_0x000a
        L_0x00a1:
            r0 = r19
            r1 = r21
            java.lang.Integer r8 = r0.openWrite(r5, r1, r6)     // Catch:{ all -> 0x00ea }
            goto L_0x0078
        L_0x00aa:
            r14 = 32
            byte[] r2 = new byte[r14]     // Catch:{ all -> 0x00df }
            r10 = 0
        L_0x00b0:
            int r14 = (r10 > r6 ? 1 : (r10 == r6 ? 0 : -1))
            if (r14 >= 0) goto L_0x00ce
            r14 = 32
            long r16 = r6 - r10
            long r14 = java.lang.Math.min(r14, r16)     // Catch:{ all -> 0x00df }
            int r3 = (int) r14     // Catch:{ all -> 0x00df }
            r14 = 0
            r9.read(r2, r14, r3)     // Catch:{ all -> 0x00df }
            int r14 = r8.intValue()     // Catch:{ all -> 0x00df }
            r0 = r19
            int r13 = r0.writeChunk(r5, r14, r2, r3)     // Catch:{ all -> 0x00df }
            long r14 = (long) r13
            long r10 = r10 + r14
            goto L_0x00b0
        L_0x00ce:
            int r14 = r8.intValue()     // Catch:{ all -> 0x00ea }
            r0 = r19
            r0.closeHandle(r5, r14)     // Catch:{ all -> 0x00ea }
            r9.close()     // Catch:{ all -> 0x00ef }
            r12.delete()     // Catch:{ IOException -> 0x0081 }
            goto L_0x000a
        L_0x00df:
            r14 = move-exception
            int r15 = r8.intValue()     // Catch:{ all -> 0x00ea }
            r0 = r19
            r0.closeHandle(r5, r15)     // Catch:{ all -> 0x00ea }
            throw r14     // Catch:{ all -> 0x00ea }
        L_0x00ea:
            r14 = move-exception
            r9.close()     // Catch:{ all -> 0x00ef }
            throw r14     // Catch:{ all -> 0x00ef }
        L_0x00ef:
            r14 = move-exception
            r12.delete()     // Catch:{ IOException -> 0x0081 }
            throw r14     // Catch:{ IOException -> 0x0081 }
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.appinventor.components.runtime.NxtDirectCommands.DownloadFile(java.lang.String, java.lang.String):void");
    }

    private Integer openWrite(String functionName, String fileName, long fileSize) {
        byte[] command = new byte[26];
        command[0] = 1;
        command[1] = -127;
        copyStringValueToBytes(fileName, command, 2, 19);
        copyULONGValueToBytes(fileSize, command, 22);
        byte[] returnPackage = sendCommandAndReceiveReturnPackage(functionName, command);
        if (evaluateStatus(functionName, returnPackage, command[1])) {
            if (returnPackage.length == 4) {
                return Integer.valueOf(getUBYTEValueFromBytes(returnPackage, 3));
            }
            Log.w(this.logTag, functionName + ": unexpected return package length " + returnPackage.length + " (expected 4)");
        }
        return null;
    }

    private int writeChunk(String functionName, int handle, byte[] buffer, int length) throws IOException {
        int writtenLength = 0;
        if (length > 32) {
            throw new IllegalArgumentException("length must be <= 32");
        }
        byte[] command = new byte[(length + 3)];
        command[0] = 1;
        command[1] = Opcode.UI_BUTTON;
        copyUBYTEValueToBytes(handle, command, 2);
        System.arraycopy(buffer, 0, command, 3, length);
        byte[] returnPackage = sendCommandAndReceiveReturnPackage(functionName, command);
        if (evaluateStatus(functionName, returnPackage, command[1])) {
            if (returnPackage.length == 6) {
                writtenLength = getUWORDValueFromBytes(returnPackage, 4);
                if (writtenLength != length) {
                    Log.e(this.logTag, functionName + ": only " + writtenLength + " bytes were written (expected " + length + ")");
                    throw new IOException("Unable to write file on robot");
                }
            } else {
                Log.w(this.logTag, functionName + ": unexpected return package length " + returnPackage.length + " (expected 6)");
            }
        }
        return writtenLength;
    }

    private void closeHandle(String functionName, int handle) {
        byte[] command = new byte[3];
        command[0] = 1;
        command[1] = Opcode.UI_DRAW;
        copyUBYTEValueToBytes(handle, command, 2);
        evaluateStatus(functionName, sendCommandAndReceiveReturnPackage(functionName, command), command[1]);
    }

    @SimpleFunction(description = "Delete a file on the robot.")
    public void DeleteFile(String fileName) {
        String functionName = "DeleteFile";
        if (checkBluetooth(functionName)) {
            if (fileName.length() == 0) {
                this.form.dispatchErrorOccurredEvent(this, functionName, ErrorMessages.ERROR_NXT_INVALID_FILE_NAME, new Object[0]);
                return;
            }
            byte[] command = new byte[22];
            command[0] = 1;
            command[1] = Opcode.TIMER_WAIT;
            copyStringValueToBytes(fileName, command, 2, 19);
            evaluateStatus(functionName, sendCommandAndReceiveReturnPackage(functionName, command), command[1]);
        }
    }

    @SimpleFunction(description = "Returns a list containing the names of matching files found on the robot.")
    public List<String> ListFiles(String wildcard) {
        String functionName = "ListFiles";
        if (!checkBluetooth(functionName)) {
            return new ArrayList();
        }
        List<String> fileNames = new ArrayList<>();
        if (wildcard.length() == 0) {
            wildcard = "*.*";
        }
        byte[] command = new byte[22];
        command[0] = 1;
        command[1] = Opcode.TIMER_READY;
        copyStringValueToBytes(wildcard, command, 2, 19);
        byte[] returnPackage = sendCommandAndReceiveReturnPackage(functionName, command);
        int status = getStatus(functionName, returnPackage, command[1]);
        while (status == 0) {
            int handle = getUBYTEValueFromBytes(returnPackage, 3);
            fileNames.add(getStringValueFromBytes(returnPackage, 4));
            byte[] command2 = new byte[3];
            command2[0] = 1;
            command2[1] = Opcode.TIMER_READ;
            copyUBYTEValueToBytes(handle, command2, 2);
            returnPackage = sendCommandAndReceiveReturnPackage(functionName, command2);
            status = getStatus(functionName, returnPackage, command2[1]);
        }
        return fileNames;
    }

    @SimpleFunction(description = "Get the firmware and protocol version numbers for the robot as a list where the first element is the firmware version number and the second element is the protocol version number.")
    public List<String> GetFirmwareVersion() {
        String functionName = "GetFirmwareVersion";
        if (!checkBluetooth(functionName)) {
            return new ArrayList();
        }
        byte[] command = {1, Opcode.BP0};
        byte[] returnPackage = sendCommandAndReceiveReturnPackage(functionName, command);
        if (!evaluateStatus(functionName, returnPackage, command[1])) {
            return new ArrayList();
        }
        List<String> versions = new ArrayList<>();
        versions.add(returnPackage[6] + "." + returnPackage[5]);
        versions.add(returnPackage[4] + "." + returnPackage[3]);
        return versions;
    }

    private Integer openWriteLinear(String functionName, String fileName, long fileSize) {
        byte[] command = new byte[26];
        command[0] = 1;
        command[1] = Opcode.BP1;
        copyStringValueToBytes(fileName, command, 2, 19);
        copyULONGValueToBytes(fileSize, command, 22);
        byte[] returnPackage = sendCommandAndReceiveReturnPackage(functionName, command);
        if (evaluateStatus(functionName, returnPackage, command[1])) {
            if (returnPackage.length == 4) {
                return Integer.valueOf(getUBYTEValueFromBytes(returnPackage, 3));
            }
            Log.w(this.logTag, functionName + ": unexpected return package length " + returnPackage.length + " (expected 4)");
        }
        return null;
    }

    @SimpleFunction(description = "Set the brick name of the robot.")
    public void SetBrickName(String name) {
        String functionName = "SetBrickName";
        if (checkBluetooth(functionName)) {
            byte[] command = new byte[18];
            command[0] = 1;
            command[1] = -104;
            copyStringValueToBytes(name, command, 2, 15);
            evaluateStatus(functionName, sendCommandAndReceiveReturnPackage(functionName, command), command[1]);
        }
    }

    @SimpleFunction(description = "Get the brick name of the robot.")
    public String GetBrickName() {
        String functionName = "GetBrickName";
        if (!checkBluetooth(functionName)) {
            return "";
        }
        byte[] command = {1, -101};
        byte[] returnPackage = sendCommandAndReceiveReturnPackage(functionName, command);
        if (evaluateStatus(functionName, returnPackage, command[1])) {
            return getStringValueFromBytes(returnPackage, 3);
        }
        return "";
    }
}
