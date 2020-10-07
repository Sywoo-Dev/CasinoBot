package fr.sywoo.casino.objs;

import java.util.HashMap;

import fr.sywoo.casino.enums.Languages;
import fr.sywoo.casino.utils.DBUtils;
import fr.sywoo.casino.utils.MessageUtils;
import net.dv8tion.jda.core.entities.User;

public class CasinoUser {

	private String uuid;
	private String username;
	private int coins;
	private Languages lang;

	public static HashMap<User, CasinoUser> users = new HashMap<>();

	public CasinoUser(User user) {
		if(user.isBot()) return;
		String uuid = user.getId();
		if(DBUtils.getString("user", "lang", "uuid", uuid) == null) {
			new MessageUtils().sendPrivateMessage(user, "Tapper la commande -register avant de pouvoir faire ceci !\n Type -register before do that !");
			return;
		}
		this.uuid = uuid;
		this.username = user.getName();
		this.coins = DBUtils.getInt("user", "coins", "uuid", uuid);
		this.lang = Languages.valueOf(DBUtils.getString("user", "lang", "uuid", uuid));
		users.put(user, this);
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getCoins() {
		return coins;
	}

	public void setCoins(int coins) {
		this.coins = coins;
	}

	public Languages getLang() {
		return lang;
	}

	public void setLang(Languages lang) {
		this.lang = lang;
	}

	public static CasinoUser getUser(User u) {
		if(u == null) {
			System.out.println("user is null"); return null;
		}
		if(!users.containsKey(u)) {
			CasinoUser c1 = new CasinoUser(u);
			return c1;
		}
		return users.get(u);
	}
	public void addCoins(int amount) {
		this.coins += amount;
		
	}
	public void removeCoins(int amount) {
		this.coins -= amount;
	}

	public void save() {
		DBUtils.updateInt("user", "coins", "uuid", uuid, coins);
		DBUtils.updateString("user", "lang", "uuid", uuid, lang.toString().toUpperCase());
	}

}
