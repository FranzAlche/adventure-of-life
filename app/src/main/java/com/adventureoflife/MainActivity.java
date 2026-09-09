package com.adventureoflife;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowInsets;
import android.widget.*;
import android.media.AudioManager;
import android.media.ToneGenerator;

public class MainActivity extends Activity {

    final int BG = Color.rgb(3,7,13);
    final int PANEL = Color.rgb(8,18,29);
    final int CYAN = Color.rgb(0,229,255);
    final int TEXT = Color.rgb(231,250,255);
    final int MUTED = Color.rgb(125,165,180);
    final int GOLD = Color.rgb(255,215,90);
    final int BORDER = Color.rgb(30,91,112);

    LinearLayout root, content, nav;
    TextView title, cold;
    ToneGenerator tone;
    int page = 0;

    @Override
    public void onCreate(Bundle b) {
        super.onCreate(b);

        tone = new ToneGenerator(AudioManager.STREAM_NOTIFICATION, 70);

        buildOpening();
    }

    @Override
    protected void onDestroy() {
        if (tone != null) tone.release();
        super.onDestroy();
    }

    void clickSound() {
        if (tone != null) {
            tone.startTone(ToneGenerator.TONE_PROP_BEEP2, 70);
        }
    }

    TextView tv(String s, float sp, int color) {
        TextView t = new TextView(this);
        t.setText(s);
        t.setTextSize(sp);
        t.setTextColor(color);
        t.setPadding(0, 3, 0, 3);
        return t;
    }

    GradientDrawable bg(int color, int stroke) {
        GradientDrawable g = new GradientDrawable();
        g.setColor(color);
        g.setCornerRadius(18);
        g.setStroke(1, stroke);
        return g;
    }

    LinearLayout card() {
        LinearLayout c = new LinearLayout(this);
        c.setOrientation(LinearLayout.VERTICAL);
        c.setPadding(18,16,18,16);
        c.setBackground(bg(PANEL, BORDER));

        LinearLayout.LayoutParams p =
            new LinearLayout.LayoutParams(-1,-2);
        p.setMargins(0,0,0,12);
        c.setLayoutParams(p);
        return c;
    }

    Button actionButton(String text, int textSize) {
        Button b = new Button(this);
        b.setText(text);
        b.setTextSize(textSize);
        b.setTextColor(TEXT);
        b.setAllCaps(false);
        b.setGravity(Gravity.CENTER);
        b.setPadding(8, 0, 8, 0);
        b.setBackground(bg(Color.rgb(7,24,36), BORDER));
        b.setOnClickListener(v -> clickSound());
        return b;
    }

    void buildOpening() {
        LinearLayout opening = new LinearLayout(this);
        opening.setOrientation(LinearLayout.VERTICAL);
        opening.setGravity(Gravity.CENTER);
        opening.setPadding(28, 40, 28, 30);
        opening.setBackgroundColor(BG);

        TextView small = tv("SYSTEM INITIALIZATION", 12, CYAN);
        small.setGravity(Gravity.CENTER);
        small.setLetterSpacing(.18f);

        TextView main = tv("ADVENTURE\nOF LIFE", 34, TEXT);
        main.setGravity(Gravity.CENTER);
        main.setTypeface(null, android.graphics.Typeface.BOLD);
        main.setLetterSpacing(.08f);

        TextView system = tv("S Y S T E M", 18, CYAN);
        system.setGravity(Gravity.CENTER);
        system.setLetterSpacing(.28f);

        TextView line = tv(
            "\nREALITY > FANTASY\n" +
            "LEVEL = PROOF\n" +
            "FAILURE = DATA",
            12, MUTED);
        line.setGravity(Gravity.CENTER);

        Space space1 = new Space(this);
        Space space2 = new Space(this);

        Button start = actionButton("▶  START SYSTEM", 17);
        start.setTextColor(CYAN);
        start.setBackground(bg(Color.rgb(6,35,48), CYAN));

        start.setOnClickListener(v -> {
            clickSound();
            buildShell();
            showPage(0);
        });

        opening.addView(small);
        opening.addView(main);
        opening.addView(system);

        opening.addView(space1,
            new LinearLayout.LayoutParams(1, 0, 1));

        opening.addView(line);

        opening.addView(space2,
            new LinearLayout.LayoutParams(1, 0, .55f));

        opening.addView(start,
            new LinearLayout.LayoutParams(-1, 64));

        setContentView(opening);
    }

    void buildShell() {
        root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setBackgroundColor(BG);

        LinearLayout top = new LinearLayout(this);
        top.setGravity(Gravity.CENTER_VERTICAL);
        top.setPadding(16, 12, 14, 10);

        LinearLayout left = new LinearLayout(this);
        left.setOrientation(LinearLayout.VERTICAL);

        TextView kicker =
            tv("ADVENTURE OF LIFE // SYSTEM", 12, CYAN);
        kicker.setLetterSpacing(.06f);

        title = tv("SYSTEM CORE", 25, TEXT);
        title.setTypeface(null, android.graphics.Typeface.BOLD);

        left.addView(kicker);
        left.addView(title);

        top.addView(left,
            new LinearLayout.LayoutParams(0,-2,1));

        cold = tv("◉  COLD\nUNKNOWN", 11, GOLD);
        cold.setGravity(Gravity.CENTER);
        cold.setPadding(10,4,10,4);
        cold.setBackground(
            bg(Color.rgb(38,31,8), Color.rgb(150,120,35)));

        top.addView(cold,
            new LinearLayout.LayoutParams(92,56));

        root.addView(top);

        ScrollView scroll = new ScrollView(this);

        content = new LinearLayout(this);
        content.setOrientation(LinearLayout.VERTICAL);
        content.setPadding(14, 6, 14, 104);

        scroll.addView(content);

        root.addView(scroll,
            new LinearLayout.LayoutParams(-1,0,1));

        nav = new LinearLayout(this);
        nav.setPadding(8, 6, 8, 8);
        nav.setGravity(Gravity.CENTER);
        nav.setBackgroundColor(Color.rgb(5,12,20));

        String[] labels = {
            "⌂\nSYSTEM",
            "♢\nSTATUS",
            "✦\nQUEST",
            "⚚\nSKILL",
            "◉\nVIKA"
        };

        for (int i = 0; i < labels.length; i++) {
            final int idx = i;

            Button b = actionButton(labels[i], 10);
            b.setOnClickListener(v -> {
                clickSound();
                showPage(idx);
            });

            LinearLayout.LayoutParams bp =
                new LinearLayout.LayoutParams(0, 58, 1);
            bp.setMargins(3, 0, 3, 0);
            nav.addView(b, bp);
        }

        root.addView(nav);

        // Keep the navigation bar above Android's system area.
        root.setOnApplyWindowInsetsListener((v, insets) -> {
            int bottom = insets.getSystemWindowInsetBottom();
            nav.setPadding(8, 6, 8, 8 + bottom);
            return insets;
        });

        setContentView(root);
        root.requestApplyInsets();
    }

    void add(View v) {
        content.addView(v);
    }

    TextView heading(String s) {
        TextView h = tv(s, 12, CYAN);
        h.setLetterSpacing(.12f);
        h.setPadding(2, 8, 2, 10);
        return h;
    }

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

        title.setText(names[p]);

        if (p == 0) home();
        if (p == 1) status();
        if (p == 2) quests();
        if (p == 3) skill();
        if (p == 4) vika();
    }

    void home() {
        LinearLayout hero = card();
        hero.setGravity(Gravity.CENTER);

        TextView n = tv("FRANZ ALCHE", 26, TEXT);
        n.setGravity(Gravity.CENTER);
        n.setTypeface(null, android.graphics.Typeface.BOLD);

        TextView sub = tv(
            "ELITE ZERO HOUR  •  RANK E",
            13, MUTED);
        sub.setGravity(Gravity.CENTER);

        hero.addView(n);
        hero.addView(sub);
        add(hero);

        LinearLayout prog = card();
        prog.addView(heading("PLAYER CORE"));
        prog.addView(tv("LEVEL   02", 18, TEXT));
        prog.addView(tv("EXP     87 / 150", 14, MUTED));

        ProgressBar pb =
            new ProgressBar(this, null,
                android.R.attr.progressBarStyleHorizontal);

        pb.setMax(150);
        pb.setProgress(87);

        prog.addView(pb,
            new LinearLayout.LayoutParams(-1,12));

        prog.addView(tv(
            "Alchemist  /  Assassin  /  Trader [Novice]",
            12, MUTED));

        add(prog);

        LinearLayout vikaPanel = card();
        vikaPanel.addView(heading("VIKA SYSTEM LINK"));
        vikaPanel.addView(tv(
            "CORE STATUS: LOCAL\n" +
            "COMMAND ENGINE: STANDBY\n" +
            "ONLINE BRAIN: PLANNED",
            13, TEXT));

        add(vikaPanel);

        LinearLayout rules = card();
        rules.addView(heading("SYSTEM PRINCIPLES"));
        rules.addView(tv(
            "REALITY > FANTASY\n" +
            "LEVEL = PROOF\n" +
            "CLAIM ≠ REAL\n" +
            "FAILURE = DATA\n" +
            "REAL STAT = MEASURED",
            13, TEXT));
        add(rules);
    }

    void status() {
        LinearLayout c = card();

        c.addView(heading("PLAYER"));
        c.addView(tv("FRANZ ALCHE", 24, TEXT));
        c.addView(tv(
            "ELITE ZERO HOUR  •  RANK E  •  LEVEL 02",
            12, MUTED));
        c.addView(tv(
            "Alchemist / Assassin / Trader [Novice]",
            13, TEXT));

        add(c);

        LinearLayout s = card();
        s.addView(heading("REAL STAT"));

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

        for (String r : rows)
            s.addView(tv(r, 13, TEXT));

        s.addView(tv(
            "\nRULE: unmeasured ≠ 0",
            11, MUTED));

        add(s);
    }

    void quests() {
        LinearLayout q = card();
        q.addView(heading("ACTIVE QUEST POOL"));

        String[] qs = {
            "🔴 OPERATION: RENTENIR ZERO\nDebt principal Rp3.000.000 → Rp0",
            "⚗️ WHY DO WE AGE?\nElixir Research ~80% • Node 03",
            "🌿 HERBAL HUNT #01\nIdentify candidate plant with evidence",
            "📈 OB REJECTION BACKTEST #11\nNext backtest • 10/20 completed",
            "🧠 ORDER & DISCIPLINE\nMind Quest • 🟡",
            "🚭 SMOKE BREAKER\n30-minute interval • Quest Mode"
        };

        for (String x : qs) {
            TextView t = tv(x, 13, TEXT);
            t.setPadding(4, 10, 4, 10);
            q.addView(t);
        }

        q.addView(tv(
            "\nQUEST BOARD = POOL\n" +
            "Execution is managed through Quest Management / Active Rotation.",
            11, MUTED));

        add(q);
    }

    void skill() {
        LinearLayout s = card();
        s.addView(heading("SKILL TREE"));

        String[] rows = {
            "🟢 Risk Management",
            "🟢 OB Rejection Analysis Lv.1",
            "🟢 Alchemist Ingredient Knowledge Lv.1",
            "🟡 Backtesting Mastery — 10/20",
            "🔒 Breakout & Retest",
            "🟡 Discipline",
            "🟡 Patience",
            "🟡 Failure Analysis"
        };

        for (String r : rows)
            s.addView(tv(r, 14, TEXT));

        s.addView(tv(
            "\nSP  0\n\n" +
            "LEARN → PRACTICE → EVIDENCE → TEST → UNLOCK",
            12, MUTED));

        add(s);
    }

    void vika() {
        LinearLayout c = card();
        c.setGravity(Gravity.CENTER_HORIZONTAL);

        c.addView(tv("◉", 52, CYAN));

        TextView v = tv("VIKA", 30, TEXT);
        v.setTypeface(null, android.graphics.Typeface.BOLD);
        c.addView(v);

        c.addView(tv(
            "SYSTEM / GAME MASTER",
            12, CYAN));

        c.addView(tv(
            "\nCORE STATUS: LOCAL PLACEHOLDER",
            13, MUTED));

        c.addView(tv(
            "\nPlanned modules:\n" +
            "• OpenAI Brain\n" +
            "• Voice\n" +
            "• Smart Signal\n" +
            "• Evidence Review\n" +
            "• Progress Tracking\n" +
            "• Quest Guidance",
            13, TEXT));

        add(c);
    }
                         }
