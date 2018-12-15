package com.mcrminer;

import com.mcrminer.ui.AbstractJavaFxApplicationSupport;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Lazy;

@Lazy
@SpringBootApplication
public class McrminerGerritApplication extends AbstractJavaFxApplicationSupport {

	public static void main(String[] args) {
		launchApp(McrminerGerritApplication.class, args);
	}

}
