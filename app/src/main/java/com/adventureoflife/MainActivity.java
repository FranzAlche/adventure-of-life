package com.adventureoflife;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.View;
import android.widget.*;

public class MainActivity extends Activity {
    final int BG = Color.rgb(3,7,13);
    final int PANEL = Color.rgb(8,18,29);
    final int PANEL2 = Color.rgb(11,27,41);
    final int CYAN = Color.rgb(0,229,255);
    final int TEXT = Color.rgb(231,250,255);
    final int MUTED = Color.rgb(125,165,180);
    final int VIOLET = Color.rgb(151,90,255);

    LinearLayout root, content, nav;
    TextView title, cold;
    int page = 0;

    @Override public void onCreate(Bundle b) {
        super.onCreate(b);
        buildShell();
        showPage(0);
    }

    TextView tv(String s, float sp, int color) {
        TextView t = new TextView(this);
        t.setText(s); t.setTextSize(sp); t.setTextColor(color);
        t.setPadding(0, 3, 0, 3);
        return t;
    }

    GradientDrawable bg(int color, int stroke) {
        GradientDrawable g = new GradientDrawable();
        g.setColor(color); g.setCornerRadius(18);
        g.setStroke(1, stroke); return g;
    }

    LinearLayout card() {
        LinearLayout c = new LinearLayout(this);
        c.setOrientation(LinearLayout.VERTICAL);
        c.setPadding(18,16,18,16);
        c.setBackground(bg(PANEL, Color.rgb(30,91,112)));
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(-1,-2);
        p.setMargins(0,0,0,12); c.setLayoutParams(p);
        return c;
    }

    void buildShell() {
        root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setBackgroundColor(BG);

        LinearLayout top = new LinearLayout(this);
        top.setGravity(Gravity.CENTER_VERTICAL);
        top.setPadding(16,12,14,10);

        LinearLayout left = new LinearLayout(this);
        left.setOrientation(LinearLayout.VERTICAL);
        TextView kicker = tv("ADVENTURE OF LIFE // SYSTEM", 10, CYAN);
        title = tv("SYSTEM CORE", 20, TEXT);
        left.addView(kicker); left.addView(title);
        top.addView(left, new LinearLayout.LayoutParams(0,-2,1));

        cold = tv("◉  COLD\nUNKNOWN", 11, Color.rgb(255,215,90));
        cold.setGravity(Gravity.CENTER);
        cold.setPadding(10,4,10,4);
        cold.setBackground(bg(Color.rgb(38,31,8), Color.rgb(150,120,35)));
        top.addView(cold, new LinearLayout.LayoutParams(92,56));
        root.addView(top);

        ScrollView scroll = new ScrollView(this);
        content = new LinearLayout(this);
        content.setOrientation(LinearLayout.VERTICAL);
        content.setPadding(14,4,14,90);
        scroll.addView(content);
        root.addView(scroll, new LinearLayout.LayoutParams(-1,0,1));

        nav = new LinearLayout(this);
        nav.setPadding(8,7,8,8);
        nav.setGravity(Gravity.CENTER);
        nav.setBackgroundColor(Color.rgb(5,12,20));
        String[] labels = {"⌂\nSYSTEM","♢\nSTATUS","✦\nQUEST","⚚\nSKILL","◉\nVIKA"};
        for (int i=0;i<labels.length;i++) {
            final int idx=i;
            Button b = new Button(this);
            b.setText(labels[i]); b.setTextSize(10); b.setTextColor(TEXT);
            b.setAllCaps(false); b.setPadding(2,0,2,0);
            b.setBackground(bg(Color.rgb(7,24,36), Color.rgb(31,89,108)));
            b.setOnClickListener(v -> showPage(idx));
            nav.addView(b, new LinearLayout.LayoutParams(0,62,1));
        }
        root.addView(nav);
        setContentView(root);
    }

    void add(View v) { content.addView(v); }

    TextView heading(String s) {
        TextView h = tv(s, 11, CYAN);
        h.setLetterSpacing(.12f);
        h.setPadding(2,8,2,10);
        return h;
    }

    void showPage(int p) {
        page=p; content.removeAllViews();
        String[] names={"SYSTEM CORE","STATUS WINDOW","QUEST BOARD","SKILL TREE","VIKA AI CORE"};
        title.setText(names[p]);

        if(p==0) home();
        if(p==1) status();
        if(p==2) quests();
        if(p==3) skill();
        if(p==4) vika();
    }

    void home() {
        LinearLayout hero=card(); hero.setGravity(Gravity.CENTER);
        TextView crest=tv("◇\nAZ",30,CYAN); crest.setGravity(Gravity.CENTER);
        crest.setPadding(0,8,0,8); hero.addView(crest);
        TextView n=tv("FRANZ ALCHE",24,TEXT); n.setGravity(Gravity.CENTER); hero.addView(n);
        TextView sub=tv("ELITE ZERO HOUR  •  RANK E",12,MUTED); sub.setGravity(Gravity.CENTER); hero.addView(sub);
        add(hero);

        LinearLayout prog=card();
        prog.addView(heading("PLAYER CORE"));
        prog.addView(tv("LEVEL   02",17,TEXT));
        prog.addView(tv("EXP     87 / 150",14,MUTED));
        ProgressBar pb=new ProgressBar(this,null,android.R.attr.progressBarStyleHorizontal);
        pb.setMax(150); pb.setProgress(87); prog.addView(pb,new LinearLayout.LayoutParams(-1,12));
        prog.addView(tv("Alchemist  /  Assassin  /  Trader [Novice]",12,MUTED));
        add(prog);

        LinearLayout rules=card(); rules.addView(heading("SYSTEM PRINCIPLES"));
        rules.addView(tv("REALITY > FANTASY\nLEVEL = PROOF\nCLAIM ≠ REAL\nFAILURE = DATA\nREAL STAT = MEASURED",13,TEXT));
        add(rules);
    }

    void status() {
        LinearLayout c=card(); c.addView(heading("PLAYER"));
        c.addView(tv("FRANZ ALCHE",24,TEXT));
        c.addView(tv("ELITE ZERO HOUR  •  RANK E  •  LEVEL 02",12,MUTED));
        c.addView(tv("Alchemist / Assassin / Trader [Novice]",13,TEXT));
        add(c);

        LinearLayout s=card(); s.addView(heading("REAL STAT"));
        String[] rows={"STR    ?    UNMEASURED","VIT    ?    UNMEASURED","AGI    ?    UNMEASURED",
                "DEX    ?    UNMEASURED","BAL    ?    UNMEASURED","MOB    ?    UNMEASURED",
                "INT    OBSERVED","WIS    ?    UNMEASURED","PER    ?    UNMEASURED","FOC    ?    UNMEASURED"};
        for(String r:rows) s.addView(tv(r,13,TEXT));
        s.addView(tv("\nRULE: unmeasured ≠ 0",11,MUTED)); add(s);
    }

    void quests() {
        LinearLayout q=card(); q.addView(heading("ACTIVE QUEST POOL"));
        String[] qs={
            "🔴 OPERATION: RENTENIR ZERO\nDebt principal Rp3.000.000 → Rp0",
            "⚗️ WHY DO WE AGE?\nElixir Research ~80% • Node 03",
            "🌿 HERBAL HUNT #01\nIdentify candidate plant with evidence",
            "📈 OB REJECTION BACKTEST #10\nProgress 9/20 • 3W / 5L / 1BE",
            "🧠 ORDER & DISCIPLINE\nMind Quest • 🟡",
            "🚭 SMOKE BREAKER\n30-minute interval • Quest Mode"
        };
        for(String x:qs) {
            TextView t=tv(x,13,TEXT); t.setPadding(4,10,4,10); q.addView(t);
        }
        q.addView(tv("\nQUEST BOARD = POOL\nExecution is managed through Quest Management / Active Rotation.",11,MUTED));
        add(q);
    }

    void skill() {
        LinearLayout s=card(); s.addView(heading("SKILL TREE"));
        String[] rows={"🟢 Risk Management","🟢 OB Rejection Analysis Lv.1","🟢 Alchemist Ingredient Knowledge Lv.1",
                "🟡 Backtesting Mastery — 9/20","🔒 Breakout & Retest","🟡 Discipline","🟡 Patience","🟡 Failure Analysis"};
        for(String r:rows) s.addView(tv(r,14,TEXT));
        s.addView(tv("\nSP  0\n\nLEARN → PRACTICE → EVIDENCE → TEST → UNLOCK",12,MUTED));
        add(s);
    }

    void vika() {
        LinearLayout c=card(); c.setGravity(Gravity.CENTER_HORIZONTAL);
        c.addView(tv("◉",52,CYAN));
        c.addView(tv("VIKA",28,TEXT));
        c.addView(tv("SYSTEM / GAME MASTER",12,CYAN));
        c.addView(tv("\nCORE STATUS: LOCAL PLACEHOLDER",13,MUTED));
        c.addView(tv("\nPlanned modules:\n• OpenAI Brain\n• Voice\n• Smart Signal\n• Evidence Review\n• Progress Tracking\n• Quest Guidance",13,TEXT));
        add(c);
    }
                   }
