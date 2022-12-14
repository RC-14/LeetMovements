package rc14.leetmovements.mixin;

import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayNetworkHandler.class)
public abstract class ServerPlayNetworkHandlerMixin {
	@Shadow private double lastTickX;
	@Shadow private double lastTickZ;
	@Shadow private double updatedX;
	@Shadow private double updatedZ;

	@Shadow public abstract void disconnect(Text reason);

	@Inject(method = "onPlayerMove(Lnet/minecraft/network/packet/c2s/play/PlayerMoveC2SPacket;)V", at = @At("TAIL"))
	private void forceLeetMovements(PlayerMoveC2SPacket packet, CallbackInfo ci) {
		if (this.updatedX == this.lastTickX && this.updatedZ == this.lastTickZ) return;

		long x = Math.round(this.updatedX * 10000000) % 10000;
		long z = Math.round(this.updatedZ * 10000000) % 10000;

		if (Math.abs(x) != 1337 || Math.abs(z) != 1337) {
			String maskedX = String.valueOf(Math.abs(this.updatedX)).replaceFirst("\\d+", "XXX");
			String maskedZ = String.valueOf(Math.abs(this.updatedZ)).replaceFirst("\\d+", "ZZZ");

			this.disconnect(Text.literal("Movements not LEET enough:").append("\nx: " + maskedX).append("\nz: " + maskedZ).append("""
                    \n
                    long x = ((long) (player.getX() * 10000000)) % 10000;
                    long z = ((long) (player.getZ() * 10000000)) % 10000;
                    if (Math.abs(x) != 1337 || Math.abs(z) != 1337) kick();"""));
		}
	}
}
