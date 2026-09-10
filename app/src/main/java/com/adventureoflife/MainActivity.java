package com.adventureoflife;

import android.app.Activity;
import android.os.Bundle;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.View;
import android.widget.*;
import android.media.AudioManager;
import android.media.ToneGenerator;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends Activity {

    final int BG = Color.rgb(3,7,13);
    final int PANEL = Color.rgb(8,18,29);
    final int CYAN = Color.rgb(0,229,255);
    final int TEXT = Color.rgb(231,250,255);
    final int MUTED = Color.rgb(125,165,180);
    final int GOLD = Color.rgb(255,215,90);
    final int BORDER = Color.rgb(30,91,112);

    final int OLD_OUTSTANDING = 251000;

    LinearLayout root, content, nav;
    TextView title, cold;
    EditText commandInput;
    ToneGenerator tone;

    SharedPreferences data;

    int page = 0;

    @Override
    public void onCreate(Bundle b) {
        super.onCreate(b);

        tone = new ToneGenerator(
            AudioManager.STREAM_NOTIFICATION,
            70
        );

        data = getSharedPreferences(
            "system_data",
            MODE_PRIVATE
        );

        initializeData();

        buildOpening();
    }

    @Override
    protected void onDestroy() {
        if (tone != null) {
            tone.release();
        }

        super.onDestroy();
    }

    // =========================================================
    // DATA ENGINE
    // =========================================================

    void initializeData() {

        if (!data.contains("current_bon")) {

            data.edit()
                .putInt("current_bon", 338000)
                .putString(
                    "transactions",
                    "Sunlight|8000;" +
                    "Bear Zero|12000;" +
                    "Wafelo|3000;" +
                    "Eggs|65000;" +
                    "Santan|7000;" +
                    "Oil 2L|55000;" +
                    "Tomato|5000;" +
                    "Carrot|5000;" +
                    "Rendang seasoning|10000;" +
                    "Tempe x2|20000;" +
                    "Chili|5000;" +
                    "Pepper|2000;" +
                    "Chili powder|2000;" +
                    "Masako|2000;" +
                    "Bread|22000;" +
                    "Pasta gigi|8000;" +
                    "Sikat gigi|7000;" +
                    "Ikan|72000;" +
                    "Gooday|8000;" +
                    "Beras 1kg|20000"
                )
                .apply();
        }
    }

    int getCurrentBon() {

        return data.getInt(
            "current_bon",
            338000
        );
    }

    int getCombinedObligation() {

        return getCurrentBon()
            + OLD_OUTSTANDING;
    }

    String money(int amount) {

        return String.format(
            Locale.US,
            "Rp%,d",
            amount
        ).replace(',', '.');
    }

    void addTransaction(
        String item,
        int amount
    ) {

        int newTotal =
            getCurrentBon() + amount;

        String old =
            data.getString(
                "transactions",
                ""
            );

        String entry =
            item + "|" + amount;

        String next;

        if (old.isEmpty()) {
            next = entry;
        } else {
            next = old + ";" + entry;
        }

        data.edit()
            .putInt(
                "current_bon",
                newTotal
            )
            .putString(
                "transactions",
                next
            )
            .apply();
    }

    String financialSummary() {

        return
            "VIKA > FINANCIAL BOARD\n\n" +

            "CURRENT BON CYCLE: " +
            money(getCurrentBon()) +

            "\n" +

            "OLD OUTSTANDING WARUNG: " +
            money(OLD_OUTSTANDING) +

            "\n" +

            "COMBINED OBLIGATION: " +
            money(getCombinedObligation()) +

            "\n\n" +

            "MODE: FINANCIAL DATA\n" +

            "STORAGE: LOCAL";
    }

    // =========================================================
    // SOUND
    // =========================================================

    void clickSound() {

        if (tone != null) {

            tone.startTone(
                ToneGenerator.TONE_PROP_BEEP2,
                70
            );
        }
    }

    // =========================================================
    // UI HELPERS
    // =========================================================

    TextView tv(
        String s,
        float sp,
        int color
    ) {

        TextView t =
            new TextView(this);

        t.setText(s);
        t.setTextSize(sp);
        t.setTextColor(color);
        t.setPadding(0,3,0,3);

        return t;
    }

    GradientDrawable bg(
        int color,
        int stroke
    ) {

        GradientDrawable g =
            new GradientDrawable();

        g.setColor(color);
        g.setCornerRadius(18);
        g.setStroke(1,stroke);

        return g;
    }

    LinearLayout card() {

        LinearLayout c =
            new LinearLayout(this);

        c.setOrientation(
            LinearLayout.VERTICAL
        );

        c.setPadding(
            18,16,18,16
        );

        c.setBackground(
            bg(PANEL,BORDER)
        );

        LinearLayout.LayoutParams p =
            new LinearLayout.LayoutParams(
                -1,-2
            );

        p.setMargins(
            0,0,0,12
        );

        c.setLayoutParams(p);

        return c;
    }

    Button actionButton(
        String text,
        int textSize
    ) {

        Button b =
            new Button(this);

        b.setText(text);
        b.setTextSize(textSize);
        b.setTextColor(TEXT);
        b.setAllCaps(false);
        b.setGravity(Gravity.CENTER);

        b.setPadding(
            8,0,8,0
        );

        b.setBackground(
            bg(
                Color.rgb(7,24,36),
                BORDER
            )
        );

        b.setOnClickListener(
            v -> clickSound()
        );

        return b;
    }

    TextView heading(String s) {

        TextView h =
            tv(s,12,CYAN);

        h.setLetterSpacing(.12f);

        h.setPadding(
            2,8,2,10
        );

        return h;
    }

    void add(View v) {

        content.addView(v);
    }

    // =========================================================
    // OPENING
    // =========================================================

    void buildOpening() {

        LinearLayout opening =
            new LinearLayout(this);

        opening.setOrientation(
            LinearLayout.VERTICAL
        );

        opening.setGravity(
            Gravity.CENTER
        );

        opening.setPadding(
            28,58,28,34
        );

        opening.setBackgroundColor(BG);

        TextView small =
            tv(
                "SYSTEM INITIALIZATION",
                12,
                CYAN
            );

        small.setGravity(
            Gravity.CENTER
        );

        small.setLetterSpacing(.18f);

        TextView main =
            tv(
                "ADVENTURE\nOF LIFE",
                34,
                TEXT
            );

        main.setGravity(
            Gravity.CENTER
        );

        main.setTypeface(
            null,
            android.graphics.Typeface.BOLD
        );

        main.setLetterSpacing(.08f);

        TextView system =
            tv(
                "S Y S T E M",
                18,
                CYAN
            );

        system.setGravity(
            Gravity.CENTER
        );

        system.setLetterSpacing(.28f);

        TextView line =
            tv(
                "\nREALITY > FANTASY\n" +
                "LEVEL = PROOF\n" +
                "FAILURE = DATA",
                12,
                MUTED
            );

        line.setGravity(
            Gravity.CENTER
        );

        Space space1 =
            new Space(this);

        Space space2 =
            new Space(this);

        Button start =
            actionButton(
                "▶  START SYSTEM",
                17
            );

        start.setTextColor(CYAN);

        start.setBackground(
            bg(
                Color.rgb(6,35,48),
                CYAN
            )
        );

        start.setOnClickListener(v -> {

            clickSound();

            buildShell();

            showPage(0);
        });

        opening.addView(small);
        opening.addView(main);
        opening.addView(system);

        opening.addView(
            space1,
            new LinearLayout.LayoutParams(
                1,0,1
            )
        );

        opening.addView(line);

        opening.addView(
            space2,
            new LinearLayout.LayoutParams(
                1,0,.55f
            )
        );

        opening.addView(
            start,
            new LinearLayout.LayoutParams(
                -1,64
            )
        );

        setContentView(opening);
    }

    // =========================================================
    // MAIN SHELL
    // =========================================================

    void buildShell() {

        getWindow().setStatusBarColor(BG);

        getWindow().setNavigationBarColor(
            Color.BLACK
        );

        if (android.os.Build.VERSION.SDK_INT >= 30) {

            getWindow()
                .setDecorFitsSystemWindows(true);
        }

        root =
            new LinearLayout(this);

        root.setOrientation(
            LinearLayout.VERTICAL
        );

        root.setBackgroundColor(BG);

        LinearLayout top =
            new LinearLayout(this);

        top.setGravity(
            Gravity.CENTER_VERTICAL
        );

        top.setPadding(
            16,34,14,12
        );

        LinearLayout left =
            new LinearLayout(this);

        left.setOrientation(
            LinearLayout.VERTICAL
        );

        TextView kicker =
            tv(
                "ADVENTURE OF LIFE // SYSTEM",
                12,
                CYAN
            );

        kicker.setLetterSpacing(.06f);

        title =
            tv(
                "SYSTEM CORE",
                29,
                TEXT
            );

        title.setTypeface(
            null,
            android.graphics.Typeface.BOLD
        );

        left.addView(kicker);
        left.addView(title);

        top.addView(
            left,
            new LinearLayout.LayoutParams(
                0,-2,1
            )
        );

        cold =
            tv(
                "◉  COLD\nUNKNOWN",
                11,
                GOLD
            );

        cold.setGravity(
            Gravity.CENTER
        );

        cold.setPadding(
            10,4,10,4
        );

        cold.setBackground(
            bg(
                Color.rgb(38,31,8),
                Color.rgb(150,120,35)
            )
        );

        top.addView(
            cold,
            new LinearLayout.LayoutParams(
                92,56
            )
        );

        root.addView(top);

        ScrollView scroll =
            new ScrollView(this);

        content =
            new LinearLayout(this);

        content.setOrientation(
            LinearLayout.VERTICAL
        );

        content.setPadding(
            14,8,14,112
        );

        scroll.addView(content);

        root.addView(
            scroll,
            new LinearLayout.LayoutParams(
                -1,0,1
            )
        );

        nav =
            new LinearLayout(this);

        nav.setPadding(
            8,8,8,10
        );

        nav.setGravity(
            Gravity.CENTER
        );

        nav.setBackgroundColor(
            Color.rgb(5,12,20)
        );

        String[] labels = {

            "⌂\nSYSTEM",
            "♢\nSTATUS",
            "✦\nQUEST",
            "⚚\nSKILL",
            "◉\nVIKA"
        };

        for (
            int i = 0;
            i < labels.length;
            i++
        ) {

            final int idx = i;

            Button b =
                actionButton(
                    labels[i],
                    12
                );

            b.setOnClickListener(
                v -> {

                    clickSound();

                    showPage(idx);
                }
            );

            LinearLayout.LayoutParams bp =
                new LinearLayout.LayoutParams(
                    0,70,1
                );

            bp.setMargins(
                3,0,3,0
            );

            nav.addView(
                b,bp
            );
        }

        root.addView(nav);

        root.setOnApplyWindowInsetsListener(
            (v,insets) -> {

                int bottom =
                    insets.getSystemWindowInsetBottom();

                nav.setPadding(
                    8,
                    8,
                    8,
                    10 + bottom
                );

                return insets;
            }
        );

        setContentView(root);

        root.requestApplyInsets();
    }

    // =========================================================
    // PAGE SYSTEM
    // =========================================================

    void showPage(int p) {

        page = p;

        content.removeAllViews();

        String[] names = {

            "SYSTEM CORE",
            "STATUS WINDOW",
            "QUEST BOARD",
            "SKILL TREE",
            "VIKA AI CORE"
        };

        title.setText(
            names[p]
        );

        if (p == 0) home();

        if (p == 1) status();

        if (p == 2) quests();

        if (p == 3) skill();

        if (p == 4) vika();
    }

    // =========================================================
    // HOME
    // =========================================================

    void home() {

        LinearLayout hero =
            card();

        hero.setGravity(
            Gravity.CENTER
        );

        TextView n =
            tv(
                "FRANZ ALCHE",
                26,
                TEXT
            );

        n.setGravity(
            Gravity.CENTER
        );

        n.setTypeface(
            null,
            android.graphics.Typeface.BOLD
        );

        TextView sub =
            tv(
                "ELITE ZERO HOUR  •  RANK E",
                13,
                MUTED
            );

        sub.setGravity(
            Gravity.CENTER
        );

        hero.addView(n);
        hero.addView(sub);

        add(hero);

        LinearLayout prog =
            card();

        prog.addView(
            heading("PLAYER CORE")
        );

        prog.addView(
            tv(
                "LEVEL   02",
                18,
                TEXT
            )
        );

        prog.addView(
            tv(
                "EXP     87 / 150",
                14,
                MUTED
            )
        );

        ProgressBar pb =
            new ProgressBar(
                this,
                null,
                android.R.attr.progressBarStyleHorizontal
            );

        pb.setMax(150);
        pb.setProgress(87);

        prog.addView(
            pb,
            new LinearLayout.LayoutParams(
                -1,12
            )
        );

        prog.addView(
            tv(
                "Alchemist  /  Assassin  /  Trader [Novice]",
                12,
                MUTED
            )
        );

        add(prog);

        LinearLayout vikaPanel =
            card();

        vikaPanel.addView(
            heading("VIKA SYSTEM LINK")
        );

        vikaPanel.addView(
            tv(
                "CORE STATUS: LOCAL\n" +
                "COMMAND ENGINE: ACTIVE\n" +
                "DATA ENGINE: ACTIVE\n" +
                "ONLINE BRAIN: PLANNED",
                13,
                TEXT
            )
        );

        add(vikaPanel);

        LinearLayout rules =
            card();

        rules.addView(
            heading("SYSTEM PRINCIPLES")
        );

        rules.addView(
            tv(
                "REALITY > FANTASY\n" +
                "LEVEL = PROOF\n" +
                "CLAIM ≠ REAL\n" +
                "FAILURE = DATA\n" +
                "REAL STAT = MEASURED",
                13,
                TEXT
            )
        );

        add(rules);
    }

    // =========================================================
    // STATUS
    // =========================================================

    void status() {

        LinearLayout c =
            card();

        c.addView(
            heading("PLAYER")
        );

        c.addView(
            tv(
                "FRANZ ALCHE",
                24,
                TEXT
            )
        );

        c.addView(
            tv(
                "ELITE ZERO HOUR  •  RANK E  •  LEVEL 02",
                12,
                MUTED
            )
        );

        c.addView(
            tv(
                "Alchemist / Assassin / Trader [Novice]",
                13,
                TEXT
            )
        );

        add(c);

        LinearLayout s =
            card();

        s.addView(
            heading("REAL STAT")
        );

        String[] rows = {

            "STR    ?    UNMEASURED",
            "VIT    ?    UNMEASURED",
            "AGI    ?    UNMEASURED",
            "DEX    ?    UNMEASURED",
            "BAL    ?    UNMEASURED",
            "MOB    ?    UNMEASURED",
            "INT    OBSERVED",
            "WIS    ?    UNMEASURED",
            "PER    ?    UNMEASURED",
            "FOC    ?    UNMEASURED"
        };

        for (String r : rows) {

            s.addView(
                tv(
                    r,
                    13,
                    TEXT
                )
            );
        }

        s.addView(
            tv(
                "\nRULE: unmeasured ≠ 0",
                11,
                MUTED
            )
        );

        add(s);
    }

    // =========================================================
    // QUEST
    // =========================================================

    void quests() {

        LinearLayout q =
            card();

        q.addView(
            heading("ACTIVE QUEST POOL")
        );

        String[] qs = {

            "🔴 OPERATION: RENTENIR ZERO\n" +
            "Debt principal Rp3.000.000 → Rp0",

            "⚗️ WHY DO WE AGE?\n" +
            "Elixir Research ~80% • Node 03",

            "🌿 HERBAL HUNT #01\n" +
            "Identify candidate plant with evidence",

            "📈 OB REJECTION BACKTEST #14\n" +
            "Active • trend-following SELL",

            "🧠 ORDER & DISCIPLINE\n" +
            "Mind Quest • 🟡",

            "🚭 SMOKE BREAKER\n" +
            "30-minute interval • Quest Mode",

            "🍳 COOKING QUEST\n" +
            "Sayur Bobor • Rendang Ikan Layang planned"
        };

        for (String x : qs) {

            TextView t =
                tv(
                    x,
                    13,
                    TEXT
                );

            t.setPadding(
                4,10,4,10
            );

            q.addView(t);
        }

        q.addView(
            tv(
                "\nQUEST BOARD = POOL\n" +
                "Execution is managed through Quest Management / Active Rotation.",
                11,
                MUTED
            )
        );

        add(q);
    }

    // =========================================================
    // SKILL
    // =========================================================

    void skill() {

        LinearLayout s =
            card();

        s.addView(
            heading("SKILL TREE")
        );

        String[] rows = {

            "🟢 Risk Management",
            "🟢 MTF Analysis Lv.1",
            "🟢 OB Rejection Analysis Lv.1",
            "🟢 R:R Calculation Lv.1",
            "🟡 Backtesting Mastery — 14/20",
            "🟡 Discipline",
            "🟡 Patience",
            "🟡 Failure Analysis",
            "🍳 Cooking — Basic Cooking Lv.1"
        };

        for (String r : rows) {

            s.addView(
                tv(
                    r,
                    14,
                    TEXT
                )
            );
        }

        s.addView(
            tv(
                "\nSP  0\n\n" +
                "LEARN → PRACTICE → EVIDENCE → TEST → UNLOCK",
                12,
                MUTED
            )
        );

        add(s);
    }

    // =========================================================
    // COMMAND ENGINE
    // =========================================================

    void executeCommand(
        String raw,
        TextView response
    ) {

        String cmd =
            raw.trim()
               .toLowerCase(Locale.US);

        if (cmd.isEmpty()) {

            response.setText(
                "VIKA > COMMAND EMPTY\n\n" +
                "Masukkan perintah."
            );

            return;
        }

        // FINANCIAL
        if (
            cmd.contains("financial") ||
            cmd.contains("keuangan") ||
            cmd.contains("finansial")
        ) {

            response.setText(
                financialSummary()
            );

            return;
        }

        // STATUS
        if (
            cmd.contains("status")
        ) {

            response.setText(

                "VIKA > STATUS WINDOW\n\n" +

                "FRANZ ALCHE\n" +
                "LEVEL 02\n" +
                "EXP 87 / 150\n" +
                "RANK E\n" +

                "CLASS: " +
                "Alchemist / Assassin / Trader [Novice]"
            );

            return;
        }

        // QUEST
        if (
            cmd.contains("quest")
        ) {

            response.setText(

                "VIKA > QUEST BOARD\n\n" +

                "ACTIVE QUEST POOL\n" +

                "• Operation Rentenir Zero\n" +
                "• Why Do We Age?\n" +
                "• Herbal Hunt #01\n" +
                "• OB Rejection Backtest #14\n" +
                "• Order & Discipline\n" +
                "• Smoke Breaker\n" +
                "• Cooking Quest"
            );

            return;
        }

        // =====================================================
        // TRANSACTION PARSER
        // =====================================================

        Pattern pattern =
            Pattern.compile(

                "^(?:beli|belanja|catat)\\s+" +
                "(.+?)\\s+" +
                "(\\d+(?:[\\.,]\\d+)?)\\s*" +
                "(k|rb|ribu)$",

                Pattern.CASE_INSENSITIVE
            );

        Matcher matcher =
            pattern.matcher(cmd);

        if (matcher.find()) {

            String item =
                matcher.group(1).trim();

            String number =
                matcher.group(2)
                    .replace(",", ".");

            String unit =
                matcher.group(3)
                    .toLowerCase(Locale.US);

            double value;

            try {

                value =
                    Double.parseDouble(number);

            } catch (Exception e) {

                response.setText(
                    "VIKA > INVALID AMOUNT"
                );

                return;
            }

            int amount =
                (int)Math.round(
                    value * 1000
                );

            addTransaction(
                item,
                amount
            );

            response.setText(

                "VIKA > TRANSACTION SAVED\n\n" +

                "ITEM     : " +
                item + "\n" +

                "AMOUNT   : " +
                money(amount) + "\n\n" +

                "CURRENT BON CYCLE: " +
                money(getCurrentBon()) + "\n" +

                "COMBINED OBLIGATION: " +
                money(getCombinedObligation()) +

                "\n\n" +

                "SAVED LOCALLY"
            );

            return;
        }

        // UNKNOWN
        response.setText(
    "VIKA > COMMAND NOT RECOGNIZED\n\n" +
    "Coba:\n" +
    "- status\n" +
    "- quest\n" +
    "- financial\n" +
    "- beli ikan 72k"
);
}
}
