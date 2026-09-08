package com.adventureoflife;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.*;

public class MainActivity extends Activity {

    LinearLayout screen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showHome();
    }

    TextView title(String text) {
        TextView t = new TextView(this);
        t.setText(text);
        t.setTextColor(Color.CYAN);
        t.setTextSize(26);
        t.setGravity(Gravity.CENTER);
        t.setPadding(10,30,10,30);
        return t;
    }

    Button menu(String text) {
        Button b = new Button(this);
        b.setText(text);
        b.setTextSize(16);
        return b;
    }

    void base(String name) {
        screen = new LinearLayout(this);
        screen.setOrientation(LinearLayout.VERTICAL);
        screen.setPadding(25,25,25,25);
        screen.setBackgroundColor(Color.rgb(8,12,18));

        screen.addView(title("⚔ ADVENTURE OF LIFE"));
        screen.addView(title(name));

        setContentView(screen);
    }

    void showHome() {
        base("SYSTEM CORE");

        Button status = menu("STATUS WINDOW");
        Button quest = menu("QUEST BOARD");
        Button job = menu("JOB SYSTEM");
        Button skill = menu("SKILL TREE");
        Button inventory = menu("INVENTORY");
        Button map = menu("ADVENTURE MAP");
        Button vika = menu("VIKA AI CORE");

        screen.addView(status);
        screen.addView(quest);
        screen.addView(job);
        screen.addView(skill);
        screen.addView(inventory);
        screen.addView(map);
        screen.addView(vika);

        status.setOnClickListener(v -> showStatus());
        quest.setOnClickListener(v -> showQuest());
        job.setOnClickListener(v -> showJob());
        skill.setOnClickListener(v -> showSkill());
        inventory.setOnClickListener(v -> showInventory());
        map.setOnClickListener(v -> showMap());
        vika.setOnClickListener(v -> showVika());
    }

    void info(String text) {
        TextView t = new TextView(this);
        t.setText(text);
        t.setTextColor(Color.WHITE);
        t.setTextSize(17);
        t.setPadding(15,20,15,20);
        screen.addView(t);
    }

    void back() {
        Button b = menu("← SYSTEM");
        screen.addView(b);
        b.setOnClickListener(v -> showHome());
    }

    void showStatus() {
        base("STATUS WINDOW");
        info("PLAYER : Franz Alche\n\n"
                + "TITLE : Elite Zero Hour\n"
                + "RANK : E\n"
                + "LEVEL : 02\n"
                + "EXP : 87 / 150\n\n"
                + "CLASS\n"
                + "Alchemist / Assassin / Trader\n\n"
                + "REAL STAT\n"
                + "STR : ?\nVIT : ?\nAGI : ?\nDEX : ?\n"
                + "INT : OBSERVED");
        back();
    }

    void showQuest() {
        base("QUEST BOARD");
        info("ACTIVE QUESTS\n\n"
                + "🔴 OPERATION: RENTENIR ZERO\n"
                + "🌿 HERBAL HUNT\n"
                + "📈 OB REJECTION BACKTEST\n"
                + "🧠 ORDER & DISCIPLINE\n"
                + "🚭 SMOKE BREAKER\n\n"
                + "RULE : EVIDENCE → PROGRESS");
        back();
    }

    void showJob() {
        base("JOB SYSTEM");
        info("TARGET JOB\n\n"
                + "Trader\n"
                + "Alchemist\n"
                + "Assassin\n\n"
                + "JOB LEVEL = PROOF\n"
                + "Skill → Practice → Evidence → Test → Unlock");
        back();
    }

    void showSkill() {
        base("SKILL TREE");
        info("🟢 RISK MANAGEMENT\n"
                + "🟢 OB REJECTION Lv.1\n"
                + "🟢 ALCHEMIST KNOWLEDGE Lv.1\n"
                + "🟡 BACKTESTING MASTERY\n"
                + "🔒 BREAKOUT & RETEST\n\n"
                + "SP : 0");
        back();
    }

    void showInventory() {
        base("INVENTORY");
        info("EQUIPMENT\n\n"
                + "◇ Clear Quartz Prism\n"
                + "◇ Hematite Ring\n"
                + "◇ Silver 925 / Platinum 18K\n\n"
                + "COLD : UNKNOWN");
        back();
    }

    void showMap() {
        base("ADVENTURE MAP");
        info("WORLD : KALIMANTAN\n\n"
                + "📍 SAWIT FRONTIER\n"
                + "└─ TOXION\n"
                + "   └─ ECONOMIC BIND\n\n"
                + "NEXT OBJECTIVE\n"
                + "ESCAPE TOXION");
        back();
    }

    void showVika() {
        base("VIKA AI CORE");
        info("VIKA\n\n"
                + "SYSTEM / GAME MASTER\n\n"
                + "STATUS : OFFLINE AI PLACEHOLDER\n\n"
                + "CORE FUNCTIONS\n"
                + "• Quest Generation\n"
                + "• Quest Analysis\n"
                + "• Evidence Review\n"
                + "• Progress Tracking\n"
                + "• Skill Guidance\n"
                + "• Job Guidance\n\n"
                + "FUTURE : OPENAI API");
        back();
    }
}
