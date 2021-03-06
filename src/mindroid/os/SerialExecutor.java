/*
 * Copyright (C) 2012 Daniel Himmelein
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package mindroid.os;

public class SerialExecutor extends Executor {
	private HandlerThread mHandlerThread;
	private Handler mHandler;
	
	public SerialExecutor() {
		mHandlerThread = new HandlerThread();
		mHandlerThread.start();
		mHandler = new Handler(mHandlerThread.getLooper());
	}
	
	public void finalize() {
		mHandlerThread.getLooper().quit();
		try {
			mHandlerThread.join();
		} catch (InterruptedException e) {
			// Ignore exceptions while shutting down.
		}
	}

	public void execute(Runnable runnable) {
		mHandler.post(runnable);
	}

	public boolean cancel(Runnable runnable) {
		return mHandler.removeCallbacks(runnable);
	}
}
