library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.STD_LOGIC_UNSIGNED.ALL;
use IEEE.STD_LOGIC_ARITH.ALL;

entity Unitate_executie is
    Port (up_buton : in std_logic;
          down_buton : in std_logic;
          reset : in std_logic;
          adauga_cifra : in std_logic;
          clk : in std_logic;
          data : in std_logic_vector(11 downto 0);
          anod :  out std_logic_vector(3 downto 0);
          segment : out std_logic_vector(6 downto 0);
          iesire_numarator : out std_logic_vector(3 downto 0));
end Unitate_executie;

architecture Behavioral of Unitate_executie is

component Counter is
    Port ( clk : in std_logic;
           up : in std_logic;   
           down : in std_logic;       
           reset : in std_logic;   
           adauga_cifra : in std_logic;    
           data_out: out std_logic_vector(3 downto 0));
end component;

component Seven_Segment is
    Port ( signal clk : in std_logic;
           signal data : in std_logic_vector(11 downto 0);
           signal seg : out std_logic_vector(6 downto 0);
           signal an : out std_logic_vector(3 downto 0));
end component;

signal iesire_counter : std_logic_vector(3 downto 0);

begin

COUNT: Counter port map(clk => clk, 
                        up => up_buton, 
                        down => down_buton,  
                        reset => reset, 
                        adauga_cifra => adauga_cifra,
                        data_out => iesire_counter);

DISPLAY: Seven_Segment port map(clk => clk,
                                data => data,
                                seg => segment,
                                an => anod);
                                         
iesire_numarator <= iesire_counter;

end Behavioral;
