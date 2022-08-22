package controller;

import view.myPanel;
import model.*;

public class gameControl implements Runnable {
    private myPanel mp;
    private int count = 0;

    public Boolean up = false;
    public Boolean rt = false;
    public Boolean dn = false;
    public Boolean lf = false;
    public Boolean shot = false;
    public Boolean def = false;
    public Boolean pulse = false;
    public boolean isShoting = false;
    soundEffect sound = new soundEffect();

    public gameControl(myPanel mp) {
        this.mp = mp;
    }

    private void iniControl() {
        up = false;
        rt = false;
        dn = false;
        lf = false;
    }

    @Override
    public void run() {
        while (true) {
            try {
                if (!mp.player.isLive) {
                    iniControl();
                }
                if (mp.player.reborn) {
                    mp.player.defense = true;
                } else {
                    if (def) {
                        iniControl();
                        mp.player.defense = true;
                    } else {
                        mp.player.defense = false;
                    }
                }

                while (up && !def) {
                    mp.player.moveUp();
                    if (rt) {
                        mp.player.setDrect(1);
                    } else if (dn) {
                        mp.player.setDrect(2);
                    } else if (lf) {
                        mp.player.setDrect(3);
                    } else {
                        mp.player.setDrect(0);
                    }

                    if (shot) {
                        if (mp.player.getBulletType() == 12) {
                            if (count < 1) {
                                mp.player.shot();
                                count++;
                                sound.shotSound();
                            }
                        } else {
                            if (mp.player.mybs.size() < 6) {
                                mp.player.shot();
                                sound.shotSound();
                            }
                        }
                    } else {
                        count = 0;
                    }
                    Thread.sleep(15);
                }

                while (dn && !def) {
                    mp.player.moveDown();
                    if (up) {
                        mp.player.setDrect(0);
                    } else if (rt) {
                        mp.player.setDrect(1);
                    } else if (lf) {
                        mp.player.setDrect(3);
                    } else {
                        mp.player.setDrect(2);
                    }

                    if (shot) {
                        if (mp.player.getBulletType() == 12) {
                            if (count < 1) {
                                mp.player.shot();
                                count++;
                                sound.shotSound();
                            }
                        } else {
                            if (mp.player.mybs.size() < 6) {
                                mp.player.shot();
                                sound.shotSound();
                            }
                        }
                    } else {
                        count = 0;
                    }
                    Thread.sleep(15);
                }

                while (rt && !def) {
                    mp.player.moveRight();
                    if (up) {
                        mp.player.setDrect(0);
                    } else if (dn) {
                        mp.player.setDrect(2);
                    } else if (lf) {
                        mp.player.setDrect(3);
                    } else {
                        mp.player.setDrect(1);
                    }

                    if (shot) {
                        if (mp.player.getBulletType() == 12) {
                            if (count < 1) {
                                mp.player.shot();
                                count++;
                                sound.shotSound();
                            } 
                        } else {
                            if (mp.player.mybs.size() < 6) {
                                mp.player.shot();
                                sound.shotSound();
                            }
                        }
                    } else {
                        count = 0;
                    }
                    Thread.sleep(15);
                }

                while (lf && !def) {
                    mp.player.moveLeft();
                    if (up) {
                        mp.player.setDrect(0);
                    } else if (rt) {
                        mp.player.setDrect(1);
                    } else if (dn) {
                        mp.player.setDrect(2);
                    } else {
                        mp.player.setDrect(3);
                    }

                    if (shot) {
                        if (mp.player.getBulletType() == 12) {
                            if (count < 1) {
                                mp.player.shot();
                                count++;
                                sound.shotSound();
                            }
                        } else {
                            if (mp.player.mybs.size() < 6) {
                                mp.player.shot();
                                sound.shotSound();
                            }
                        }
                    } else {
                        count = 0;
                    }
                    Thread.sleep(15);
                }
                
                if (shot) {
                    if (mp.player.getBulletType() == 12) {
                        if (count < 1) {
                            mp.player.shot();
                            count++;
                            sound.shotSound();
                        }
                    } else {
                        if (mp.player.mybs.size() < 6) {
                            mp.player.shot();
                            sound.shotSound();
                        }
                    }
                } else {
                    count = 0;
                }
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}