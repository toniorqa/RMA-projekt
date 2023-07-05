
package com.example.tonikarimovicapp.webrtc.audio

typealias AudioDeviceChangeListener = (
    audioDevices: List<AudioDevice>,
    selectedAudioDevice: AudioDevice?
) -> Unit
