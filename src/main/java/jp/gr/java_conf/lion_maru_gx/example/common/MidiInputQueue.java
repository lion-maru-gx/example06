package jp.gr.java_conf.lion_maru_gx.example.common;

import java.util.LinkedList;

import javax.sound.midi.MidiMessage;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.SysexMessage;

/**
 *
 * @author lion-maru-gx
 *
 */
public class MidiInputQueue implements Receiver {
	/**
	 * 入力メッセージキュー
	 */
	private LinkedList<MidiMessage> inputMidiEventQueue = new LinkedList<>();

	/**
	 * システム・エクスクルーシブ・メッセージの有効／無効
	 */
	private boolean sysexMessageActive = true;
	/**
	 * チャンネル・ボイス・メッセージの有効／無効
	 */
	/**
	 * NOTE_OFFの有効／無効
	 */
	private boolean noteOffActive = true;
	/**
	 * NOTE_ONの有効／無効
	 */
	private boolean noteOnActive = true;
	/**
	 * POLY_PRESSUREの有効／無効
	 */
	private boolean polyPressureActive = true;
	/**
	 * CONTROL_CHANGEの有効／無効
	 */
	private boolean controlChangeActive = true;
	/**
	 * PROGRAM_CHANGEの有効／無効
	 */
	private boolean programChangeActive = true;
	/**
	 * CHANNEL_PRESSUREの有効／無効
	 */
	private boolean channelPressureActive = true;
	/**
	 * PITCH_BENDの有効／無効
	 */
	private boolean pitchBendActive = true;
	/**
	 * システム・コモン・メッセージの有効／無効
	 */
	/**
	* MIDI_TIME_CODEの有効／無効
	*/
	private boolean midiTimeCodeActive = true;
	/**
	* SONG_POSITION_POINTERの有効／無効
	*/
	private boolean songPositionPointerActive = true;
	/**
	* SONG_SELECTの有効／無効
	*/
	private boolean songSelectActive = true;
	/**
	* TUNE_REQUESTの有効／無効
	*/
	private boolean tuneRequestActive = true;
	/**
	* END_OF_EXCLUSIVEの有効／無効
	*/
	private boolean endOfExclusiveActive = true;
	/**
	 * システム・リアルタイムメッセージの有効／無効
	 */
	/**
	 * TIMING_CLOCK://0xF8
	 */
	private boolean timingClockActive = true;
	/**
	 * START://0xFA
	 */
	private boolean startActive = true;
	/**
	 * CONTINUE://0xFB
	 */
	private boolean continueActive = true;
	/**
	 * STOP://0xFC
	 */
	private boolean stopActive = true;
	/**
	 * ACTIVE_SENSING://0xFE
	 */
	private boolean activeSensingActive = true;
	/**
	 * SYSTEM_RESET://0xFF
	 */
	private boolean systemResetActive = true;

	@Override
	public void send(MidiMessage message, long timeStamp) {
		if (message != null) {
			// 受信メッセージをフィルタリングします。
			if (message instanceof ShortMessage) {
				switch (message.getStatus()) {
				case ShortMessage.NOTE_OFF: // 0x80
					if (!isNoteOffActive()) {
						return;
					}
					break;
				case ShortMessage.NOTE_ON: // 0x90
					if (!isNoteOnActive()) {
						return;
					}
					break;
				case ShortMessage.POLY_PRESSURE: //0xA0
					if (!isPolyPressureActive()) {
						return;
					}
					break;
				case ShortMessage.CONTROL_CHANGE://0xB0
					if (!isControlChangeActive()) {
						return;
					}
					break;
				case ShortMessage.PROGRAM_CHANGE://0xC0
					if (!isProgramChangeActive()) {
						return;
					}
					break;
				case ShortMessage.CHANNEL_PRESSURE://0xD0
					if (!isChannelPressureActive()) {
						return;
					}
					break;
				case ShortMessage.PITCH_BEND://0xE0
					if (!isPitchBendActive()) {
						return;
					}
					break;
				case ShortMessage.MIDI_TIME_CODE://0xF1
					if (!isMidiTimeCodeActive()) {
						return;
					}
					break;
				case ShortMessage.SONG_POSITION_POINTER://0xF2
					if (!isSongPositionPointerActive()) {
						return;
					}
					break;
				case ShortMessage.SONG_SELECT://0xF3
					if (!isSongSelectActive()) {
						return;
					}
					break;
				case ShortMessage.TUNE_REQUEST://0xF6
					if (!this.isTuneRequestActive()) {
						return;
					}
				case ShortMessage.END_OF_EXCLUSIVE://0xF7
					if (!this.isEndOfExclusiveActive()) {
						return;
					}
					break;
				case ShortMessage.TIMING_CLOCK://0xF8
					if (!isTimingClockActive()) {
						return;
					}
					break;
				case ShortMessage.START://0xFA
					if (!isStartActive()) {
						return;
					}
					break;
				case ShortMessage.CONTINUE://0xFB
					if (!isContinueActive()) {
						return;
					}
					break;
				case ShortMessage.STOP://0xFC
					if (!isStopActive()) {
						return;
					}
					break;
				case ShortMessage.ACTIVE_SENSING://0xFE
					if (!isActiveSensingActive()) {
						return;
					}
					break;
				case ShortMessage.SYSTEM_RESET://0xFF
					if (!this.isSystemResetActive()) {
						return;
					}
					break;
				}
			} else if (message instanceof SysexMessage) {
				if (!isSysexMessageActive()) {
					return;
				}
			}
			inputMidiEventQueue.offer(message);
		}
	}

	@Override
	public void close() {
		inputMidiEventQueue.clear();
	}

	/**
	 * 入力メッセージの取得します。
	 *
	 * @return
	 */
	public MidiMessage getInputMessage() {
		if (inputMidiEventQueue.isEmpty()) {
			return null;
		}
		return inputMidiEventQueue.poll();
	}

	/**
	 * チャンネル・ボイス・メッセージの有効／無効を設定します。
	 * @param chanelVoiceMessageActive
	 */
	public void setChanelVoiceMessageActive(boolean chanelVoiceMessageActive) {
		noteOffActive = chanelVoiceMessageActive;
		noteOnActive = chanelVoiceMessageActive;
		polyPressureActive = chanelVoiceMessageActive;
		controlChangeActive = chanelVoiceMessageActive;
		programChangeActive = chanelVoiceMessageActive;
		channelPressureActive = chanelVoiceMessageActive;
		pitchBendActive = chanelVoiceMessageActive;
	}

	/**
	 * システム・リアルタイムメッセージの有効／無効を設定します。
	 * @param systemRealtimeMessageActive
	 */
	public void setSystemRealtimeMessageActive(boolean systemRealtimeMessageActive) {
		timingClockActive = systemRealtimeMessageActive;
		startActive = systemRealtimeMessageActive;
		continueActive = systemRealtimeMessageActive;
		stopActive = systemRealtimeMessageActive;
		activeSensingActive = systemRealtimeMessageActive;
		systemResetActive = systemRealtimeMessageActive;
	}

	/**
	 * システム・コモン・メッセージの有効／無効を設定します。
	 * @param systemCommonMessageActive
	 */
	public void setSystemCommonMessageActive(boolean systemCommonMessageActive) {
		midiTimeCodeActive = systemCommonMessageActive;
		songPositionPointerActive = systemCommonMessageActive;
		songSelectActive = systemCommonMessageActive;
		tuneRequestActive = systemCommonMessageActive;
		endOfExclusiveActive = systemCommonMessageActive;
	}

	/**
	 * SysexMessageの有効／無効を取得します。
	 * @return
	 */
	public boolean isSysexMessageActive() {
		return sysexMessageActive;
	}

	/**
	 * SysexMessageの有効／無効を設定します。
	 * @param sysexMessageActive
	 */
	public void setSysexMessageActive(boolean sysexMessageActive) {
		this.sysexMessageActive = sysexMessageActive;
	}

	/**
	 * @return inputMidiEventQueue
	 */
	public LinkedList<MidiMessage> getInputMidiEventQueue() {
		return inputMidiEventQueue;
	}

	/**
	 * @param inputMidiEventQueue セットする inputMidiEventQueue
	 */
	public void setInputMidiEventQueue(LinkedList<MidiMessage> inputMidiEventQueue) {
		this.inputMidiEventQueue = inputMidiEventQueue;
	}

	/**
	 * @return noteOffActive
	 */
	public boolean isNoteOffActive() {
		return noteOffActive;
	}

	/**
	 * @param noteOffActive セットする noteOffActive
	 */
	public void setNoteOffActive(boolean noteOffActive) {
		this.noteOffActive = noteOffActive;
	}

	/**
	 * @return noteOnActive
	 */
	public boolean isNoteOnActive() {
		return noteOnActive;
	}

	/**
	 * @param noteOnActive セットする noteOnActive
	 */
	public void setNoteOnActive(boolean noteOnActive) {
		this.noteOnActive = noteOnActive;
	}

	/**
	 * @return polyPressureActive
	 */
	public boolean isPolyPressureActive() {
		return polyPressureActive;
	}

	/**
	 * @param polyPressureActive セットする polyPressureActive
	 */
	public void setPolyPressureActive(boolean polyPressureActive) {
		this.polyPressureActive = polyPressureActive;
	}

	/**
	 * @return controlChangeActive
	 */
	public boolean isControlChangeActive() {
		return controlChangeActive;
	}

	/**
	 * @param controlChangeActive セットする controlChangeActive
	 */
	public void setControlChangeActive(boolean controlChangeActive) {
		this.controlChangeActive = controlChangeActive;
	}

	/**
	 * @return programChangeActive
	 */
	public boolean isProgramChangeActive() {
		return programChangeActive;
	}

	/**
	 * @param programChangeActive セットする programChangeActive
	 */
	public void setProgramChangeActive(boolean programChangeActive) {
		this.programChangeActive = programChangeActive;
	}

	/**
	 * @return channelPressureActive
	 */
	public boolean isChannelPressureActive() {
		return channelPressureActive;
	}

	/**
	 * @param channelPressureActive セットする channelPressureActive
	 */
	public void setChannelPressureActive(boolean channelPressureActive) {
		this.channelPressureActive = channelPressureActive;
	}

	/**
	 * @return pitchBendActive
	 */
	public boolean isPitchBendActive() {
		return pitchBendActive;
	}

	/**
	 * @param pitchBendActive セットする pitchBendActive
	 */
	public void setPitchBendActive(boolean pitchBendActive) {
		this.pitchBendActive = pitchBendActive;
	}

	/**
	 * @return midiTimeCodeActive
	 */
	public boolean isMidiTimeCodeActive() {
		return midiTimeCodeActive;
	}

	/**
	 * @param midiTimeCodeActive セットする midiTimeCodeActive
	 */
	public void setMidiTimeCodeActive(boolean midiTimeCodeActive) {
		this.midiTimeCodeActive = midiTimeCodeActive;
	}

	/**
	 * @return songPositionPointerActive
	 */
	public boolean isSongPositionPointerActive() {
		return songPositionPointerActive;
	}

	/**
	 * @param songPositionPointerActive セットする songPositionPointerActive
	 */
	public void setSongPositionPointerActive(boolean songPositionPointerActive) {
		this.songPositionPointerActive = songPositionPointerActive;
	}

	/**
	 * @return songSelectActive
	 */
	public boolean isSongSelectActive() {
		return songSelectActive;
	}

	/**
	 * @param songSelectActive セットする songSelectActive
	 */
	public void setSongSelectActive(boolean songSelectActive) {
		this.songSelectActive = songSelectActive;
	}

	/**
	 * @return tuneRequestActive
	 */
	public boolean isTuneRequestActive() {
		return tuneRequestActive;
	}

	/**
	 * @param tuneRequestActive セットする tuneRequestActive
	 */
	public void setTuneRequestActive(boolean tuneRequestActive) {
		this.tuneRequestActive = tuneRequestActive;
	}

	/**
	 * @return endOfExclusiveActive
	 */
	public boolean isEndOfExclusiveActive() {
		return endOfExclusiveActive;
	}

	/**
	 * @param endOfExclusiveActive セットする endOfExclusiveActive
	 */
	public void setEndOfExclusiveActive(boolean endOfExclusiveActive) {
		this.endOfExclusiveActive = endOfExclusiveActive;
	}

	/**
	 * @return timingClockActive
	 */
	public boolean isTimingClockActive() {
		return timingClockActive;
	}

	/**
	 * @param timingClockActive セットする timingClockActive
	 */
	public void setTimingClockActive(boolean timingClockActive) {
		this.timingClockActive = timingClockActive;
	}

	/**
	 * @return startActive
	 */
	public boolean isStartActive() {
		return startActive;
	}

	/**
	 * @param startActive セットする startActive
	 */
	public void setStartActive(boolean startActive) {
		this.startActive = startActive;
	}

	/**
	 * @return continueActive
	 */
	public boolean isContinueActive() {
		return continueActive;
	}

	/**
	 * @param continueActive セットする continueActive
	 */
	public void setContinueActive(boolean continueActive) {
		this.continueActive = continueActive;
	}

	/**
	 * @return stopActive
	 */
	public boolean isStopActive() {
		return stopActive;
	}

	/**
	 * @param stopActive セットする stopActive
	 */
	public void setStopActive(boolean stopActive) {
		this.stopActive = stopActive;
	}

	/**
	 * @return activeSensingActive
	 */
	public boolean isActiveSensingActive() {
		return activeSensingActive;
	}

	/**
	 * @param activeSensingActive セットする activeSensingActive
	 */
	public void setActiveSensingActive(boolean activeSensingActive) {
		this.activeSensingActive = activeSensingActive;
	}

	/**
	 * @return systemResetActive
	 */
	public boolean isSystemResetActive() {
		return systemResetActive;
	}

	/**
	 * @param systemResetActive セットする systemResetActive
	 */
	public void setSystemResetActive(boolean systemResetActive) {
		this.systemResetActive = systemResetActive;
	}

}
