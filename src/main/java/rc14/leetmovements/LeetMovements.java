package rc14.leetmovements;

import com.mojang.logging.LogUtils;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;

public class LeetMovements implements ModInitializer {

	private static final Logger LOGGER = LogUtils.getLogger();

	@Override
	public void onInitialize() {
		LOGGER.info("Initialized LeetMovements");
	}
}
