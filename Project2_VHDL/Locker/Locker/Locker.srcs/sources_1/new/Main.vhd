library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.STD_LOGIC_UNSIGNED.ALL;
use IEEE.STD_LOGIC_ARITH.ALL;

entity Main is
    Port (adauga_cifra : in std_logic;
          up_buton : in std_logic;
          down_buton : in std_logic;
          clk : in std_logic;
          reset : in std_logic;
          liber_ocupat : out std_logic;
          introdu_caractere : out std_logic;
          anod : out std_logic_vector(3 downto 0);
          segment : out std_logic_vector(6 downto 0));
end Main;

architecture Behavioral of Main is

component MPG is
    Port ( btn : in STD_LOGIC;
           clk : in STD_LOGIC;
           btn_div : out STD_LOGIC);
end component;

component Unitate_executie is
    Port (up_buton : in std_logic;
          down_buton : in std_logic;
          reset : in std_logic;
          adauga_cifra : in std_logic;
          clk : in std_logic;
          data : in std_logic_vector(11 downto 0);
          anod : out std_logic_vector(3 downto 0);
          segment : out std_logic_vector(6 downto 0);
          iesire_numarator : out std_logic_vector(3 downto 0));
end component;

component Unitate_control is
    Port ( adauga_cifra : in std_logic;
           reset : in std_logic;
           clk : in std_logic;
           iesire_numarator : in std_logic_vector(3 downto 0);
           liber_ocupat : out std_logic;
           led_introdu : out std_logic;
           data : out std_logic_vector(11 downto 0));
end component;

signal Up_Div, Down_Div, Adauga_cifra_Div : std_logic;
signal iesire_counter : std_logic_vector(3 downto 0);
signal data_intermediar : std_logic_vector(11 downto 0);

begin

MPG_UP : MPG port map(btn => up_buton,
                     clk => clk,
                     btn_div => Up_Div);
                     
MPG_DOWN : MPG port map(btn => down_buton,
                       clk => clk,
                       btn_div => Down_Div);
                       
MPG_ADAUGA : MPG port map(btn => adauga_cifra,
                         clk => clk,
                         btn_div => Adauga_cifra_Div);
                         
UC : Unitate_control port map(adauga_cifra => Adauga_cifra_Div,
                              reset => reset,       
                              clk => clk, 
                              iesire_numarator => iesire_counter,
                              liber_ocupat => liber_ocupat,
                              led_introdu => introdu_caractere,
                              data => data_intermediar);
                                           
UE : Unitate_executie port map(up_buton => Up_Div,
                               down_buton => Down_Div,
                               reset => reset,
                               adauga_cifra => Adauga_cifra_Div,
                               clk => clk,
                               data => data_intermediar,
                               anod => anod,
                               segment => segment,
                               iesire_numarator => iesire_counter);
                                            
end Behavioral;
